package com.cms.user.resource;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import com.cms.user.constants.DatabaseConstant.UserRole;
import com.cms.user.constants.DatabaseConstant.UserStatus;
import com.cms.user.dto.CommonApiResponse;
import com.cms.user.dto.UserDetailResponseDto;
import com.cms.user.dto.UserList;
import com.cms.user.dto.UserListResponseDto;
import com.cms.user.dto.UserLoginRequest;
import com.cms.user.dto.UserLoginResponse;
import com.cms.user.dto.UserRegisterDto;
import com.cms.user.entity.User;
import com.cms.user.service.CustomUserDetailsService;
import com.cms.user.service.UserService;
import com.cms.user.utility.JwtUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class UserResource {

	@Autowired
	private UserService userService;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private CustomUserDetailsService customUserDetailsService;

	@Autowired
	private JwtUtil jwtUtil;

	ObjectMapper objectMapper = new ObjectMapper();
	
	public ResponseEntity<CommonApiResponse> registerAdmin(UserRegisterDto userRegisterDto) {

		CommonApiResponse response = new CommonApiResponse();

		if (userRegisterDto == null) {
			response.setResponseMessage("bad request - missing input");
			response.setSuccess(false);

			return new ResponseEntity<CommonApiResponse>(response, HttpStatus.BAD_REQUEST);
		}

		User existingUser = this.userService.getUserByEmailAndRole(userRegisterDto.getEmailId(),
				userRegisterDto.getRole());

		if (existingUser != null) {
			response.setResponseMessage("Admin with this email id already registered");
			response.setSuccess(false);

			return new ResponseEntity<CommonApiResponse>(response, HttpStatus.BAD_REQUEST);
		}

		User user = new User();
        user.setEmailId(userRegisterDto.getEmailId());
		user.setStatus(UserStatus.ACTIVE.value());

		String encodedPassword = passwordEncoder.encode(userRegisterDto.getPassword());

		user.setPassword(encodedPassword);

		existingUser = this.userService.registerUser(user);

		if (existingUser == null) {
			response.setResponseMessage("failed to register admin");
			response.setSuccess(false);

			return new ResponseEntity<CommonApiResponse>(response, HttpStatus.BAD_REQUEST);
		}

		response.setResponseMessage("Admin registered Successfully");
		response.setSuccess(true);

		return new ResponseEntity<CommonApiResponse>(response, HttpStatus.OK);
	}

	public ResponseEntity<CommonApiResponse> registerUser(UserRegisterDto registerRequest) {

		CommonApiResponse response = new CommonApiResponse();

		if (registerRequest == null) {
			response.setResponseMessage("user is null");
			response.setSuccess(false);

			return new ResponseEntity<CommonApiResponse>(response, HttpStatus.BAD_REQUEST);
		}

		User existingUser = this.userService.getUserByEmailAndRole(registerRequest.getEmailId(),
				registerRequest.getRole());

		if (existingUser != null) {
			response.setResponseMessage("User already register");
			response.setSuccess(false);

			return new ResponseEntity<CommonApiResponse>(response, HttpStatus.BAD_REQUEST);
		}

		User user = UserRegisterDto.toEntity(registerRequest);

		user.setStatus(UserStatus.ACTIVE.value());
		user.setRole(registerRequest.getRole());

		String encodedPassword = passwordEncoder.encode(user.getPassword());

		user.setPassword(encodedPassword);

		existingUser = this.userService.registerUser(user);

		if (existingUser == null) {
			response.setResponseMessage("failed to register user");
			response.setSuccess(false);

			return new ResponseEntity<CommonApiResponse>(response, HttpStatus.BAD_REQUEST);
		}

		response.setResponseMessage("User registered Successfully");
		response.setSuccess(true);

		return new ResponseEntity<CommonApiResponse>(response, HttpStatus.OK);
	}

	public ResponseEntity<UserLoginResponse> login(UserLoginRequest loginRequest) {

		UserLoginResponse response = new UserLoginResponse();

		if (loginRequest == null) {
			response.setResponseMessage("Missing Input");
			response.setSuccess(false);

			return new ResponseEntity<UserLoginResponse>(response, HttpStatus.BAD_REQUEST);
		}

		String jwtToken = null;
		User user = null;
		try {
			authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(loginRequest.getEmailId(), loginRequest.getPassword()));
		} catch (Exception ex) {
			response.setResponseMessage("Invalid email or password.");
			response.setSuccess(false);
			return new ResponseEntity(response, HttpStatus.BAD_REQUEST);
		}

		UserDetails userDetails = customUserDetailsService.loadUserByUsername(loginRequest.getEmailId());

		user = userService.getUserByEmail(loginRequest.getEmailId());

		if (user.getStatus() != UserStatus.ACTIVE.value()) {
			response.setResponseMessage("Failed to login");
			response.setSuccess(false);
			return new ResponseEntity(response, HttpStatus.BAD_REQUEST);
		}

		for (GrantedAuthority grantedAuthory : userDetails.getAuthorities()) {
			if (grantedAuthory.getAuthority().equals(loginRequest.getRole())) {
				jwtToken = jwtUtil.generateToken(userDetails.getUsername());
			}
		}

		// user is authenticated
		if (jwtToken != null) {
			response.setUser(user);

			response.setResponseMessage("Logged in sucessful");
			response.setSuccess(true);
			response.setJwtToken(jwtToken);
			return new ResponseEntity(response, HttpStatus.OK);

		}

		else {

			response.setResponseMessage("Failed to login");
			response.setSuccess(false);
			return new ResponseEntity(response, HttpStatus.BAD_REQUEST);
		}

	}

	public ResponseEntity<CommonApiResponse> deleteUser(int userId) {

		CommonApiResponse response = new CommonApiResponse();

		if (userId == 0) {
			response.setResponseMessage("User Id is 0");
			response.setSuccess(false);

			return new ResponseEntity<CommonApiResponse>(response, HttpStatus.BAD_REQUEST);
		}

		User user = this.userService.getUserById(userId);

		user.setStatus(com.cms.user.constants.DatabaseConstant.UserStatus.NOT_ACTIVE.value());

		User deletedUser = this.userService.updateUser(user);

		if (deletedUser == null) {
			response.setResponseMessage("Failed to delete the user");
			response.setSuccess(false);

			return new ResponseEntity<CommonApiResponse>(response, HttpStatus.BAD_REQUEST);
		}

		response.setResponseMessage("User deleted Successfully");
		response.setSuccess(true);

		return new ResponseEntity<CommonApiResponse>(response, HttpStatus.OK);
	}

	public ResponseEntity<CommonApiResponse> updateUser(UserRegisterDto registerRequest) {

		CommonApiResponse response = new CommonApiResponse();

		if (registerRequest == null) {
			response.setResponseMessage("Missing input");
			response.setSuccess(false);

			return new ResponseEntity<CommonApiResponse>(response, HttpStatus.BAD_REQUEST);
		}

		User userDetailToUpdate = new User();

		User existingUser = this.userService.getUserById(registerRequest.getId());

		if (existingUser == null) {
			response.setResponseMessage("User not found");
			response.setSuccess(false);

			return new ResponseEntity<CommonApiResponse>(response, HttpStatus.BAD_REQUEST);
		}

		userDetailToUpdate.setId(existingUser.getId());
		userDetailToUpdate.setPassword(existingUser.getPassword());
		userDetailToUpdate.setEmailId(existingUser.getEmailId());
		userDetailToUpdate.setRole(existingUser.getRole());
		userDetailToUpdate.setStatus(existingUser.getStatus());
		userDetailToUpdate.setAge(registerRequest.getAge());
		userDetailToUpdate.setCity(registerRequest.getCity());
		userDetailToUpdate.setContact(registerRequest.getContact());
		userDetailToUpdate.setFirstName(registerRequest.getFirstName());
		userDetailToUpdate.setGender(registerRequest.getGender());
		userDetailToUpdate.setLastName(registerRequest.getLastName());
		userDetailToUpdate.setPincode(registerRequest.getPincode());
		userDetailToUpdate.setStreet(registerRequest.getStreet());

		User updatedUser = this.userService.updateUser(userDetailToUpdate);

		if (updatedUser == null) {
			response.setResponseMessage("Failed to update the user");
			response.setSuccess(false);

			return new ResponseEntity<CommonApiResponse>(response, HttpStatus.BAD_REQUEST);
		}

		response.setResponseMessage("User updated Successfully");
		response.setSuccess(true);

		return new ResponseEntity<CommonApiResponse>(response, HttpStatus.OK);
	}

	public ResponseEntity<UserDetailResponseDto> getUserDetailsById(int userId) {

		UserDetailResponseDto response = new UserDetailResponseDto();

		if (userId == 0) {
			response.setResponseMessage("User Id is 0");
			response.setSuccess(false);

			return new ResponseEntity<UserDetailResponseDto>(response, HttpStatus.BAD_REQUEST);
		}

		User user = this.userService.getUserById(userId);

		if (user == null) {
			response.setResponseMessage("User not found");
			response.setSuccess(false);

			return new ResponseEntity<UserDetailResponseDto>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		response.setUser(user);
		response.setResponseMessage("User Fetched Successfully");
		response.setSuccess(true);

		return new ResponseEntity<UserDetailResponseDto>(response, HttpStatus.OK);
	}

	public ResponseEntity<UserListResponseDto> getAllUsers(String role) {

		UserListResponseDto response = new UserListResponseDto();

		List<UserList> users = new ArrayList<>();

		List<User> listOfUser = this.userService.getUsersByRoleAndStatus(role, UserStatus.ACTIVE.value());
		
		if(CollectionUtils.isEmpty(listOfUser)) {
			response.setResponseMessage("No Users found!!!");
			response.setSuccess(false);

			return new ResponseEntity<UserListResponseDto>(response, HttpStatus.OK);
		}

		for (User user : listOfUser) {

			UserList userList = new UserList();
			userList.setUser(user);
			users.add(userList);
		}

		response.setUsers(users);
		response.setResponseMessage("User Fetched Successfully");
		response.setSuccess(true);

		return new ResponseEntity<UserListResponseDto>(response, HttpStatus.OK);
	}

}
