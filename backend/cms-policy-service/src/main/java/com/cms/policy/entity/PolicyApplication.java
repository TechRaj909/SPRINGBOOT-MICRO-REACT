package com.cms.policy.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;

import com.cms.policy.dto.User;

import lombok.Data;

@Entity
@Data
public class PolicyApplication {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@ManyToOne
	@JoinColumn(name = "policy_id")
	private Policy policy;

	private int customerId;

	private String applicationDate;  // epoch time

	private String startDate; // set by insurance company

	private String endDate; // set by insurance company

	private String status; // insurance company will update the status

	@Transient
	private User user;
	
}
