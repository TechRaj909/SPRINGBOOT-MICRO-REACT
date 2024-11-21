package com.cms.user.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cms.user.dto.CommonApiResponse;
import com.cms.user.dto.UserDetailResponseDto;
import com.cms.user.dto.UserListResponseDto;
import com.cms.user.dto.UserLoginRequest;
import com.cms.user.dto.UserLoginResponse;
import com.cms.user.dto.UserRegisterDto;
import com.cms.user.resource.UserResource;

@RestController
@RequestMapping("/api/user/")
//@CrossOrigin(origins = "http://localhost:3000")
public class UserController {

	@Autowired
	private UserResource userResource;

	@PostMapping("/admin/register")
	public ResponseEntity<CommonApiResponse> registerAdmin(@RequestBody UserRegisterDto userRegisterDto) {
		return userResource.registerAdmin(userRegisterDto);
	}
	
	@PostMapping("/register")
	public ResponseEntity<CommonApiResponse> registerUser(@RequestBody UserRegisterDto userRegisterDto) {
		return userResource.registerUser(userRegisterDto);
	}
	
	@PostMapping("login")	
	public ResponseEntity<UserLoginResponse> login(@RequestBody UserLoginRequest userLoginRequest) {
		return userResource.login(userLoginRequest);
	}
	
	@GetMapping("/fetch")
	public ResponseEntity<UserDetailResponseDto> fetchUser(@RequestParam("userId") int userId) {
		return userResource.getUserDetailsById(userId);
	}
	
	@DeleteMapping("/delete")
	public ResponseEntity<CommonApiResponse> deleteUser(@RequestParam("userId") int userId) {
		return userResource.deleteUser(userId);
	}
	
	@PutMapping("/update")
	public ResponseEntity<CommonApiResponse> updateUser(UserRegisterDto userRegisterDto) {
		return userResource.updateUser(userRegisterDto);
	}
	
	@GetMapping("/fetch/all")
	public ResponseEntity<UserListResponseDto> fetchAllUsers(@RequestParam("role") String role) {
		return userResource.getAllUsers(role);
	}

}
