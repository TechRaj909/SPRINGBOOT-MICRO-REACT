package com.cms.user.dto;

import org.springframework.beans.BeanUtils;

import com.cms.user.entity.User;

import lombok.Data;

@Data
public class UserRegisterDto {
	
	private int id;

	private String firstName;

	private String lastName;

	private int age;

	private String gender;

	private String emailId;

	private String contact;

	private String street;

	private String city;

	private String pincode;

	private String password;

	private String role;
	
	public static User toEntity(UserRegisterDto userRegisterDto) {
		User user = new User();
		BeanUtils.copyProperties(userRegisterDto, user);
		return user;
	}

}
