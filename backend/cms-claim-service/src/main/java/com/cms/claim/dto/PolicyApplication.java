package com.cms.claim.dto;

import lombok.Data;

@Data
public class PolicyApplication {

	private int id;

	private Policy policy;

	private int customerId;

	private String applicationDate;  // epoch time

	private String startDate; // set by insurance company

	private String endDate; // set by insurance company

	private String status; // insurance company will update the status

	private User user;
	
}
