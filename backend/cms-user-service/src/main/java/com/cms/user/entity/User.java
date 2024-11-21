package com.cms.user.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import lombok.Data;

@Entity
@Data
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
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

	private int status;

}
