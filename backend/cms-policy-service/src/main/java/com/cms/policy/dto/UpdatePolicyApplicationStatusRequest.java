package com.cms.policy.dto;

import lombok.Data;

@Data
public class UpdatePolicyApplicationStatusRequest {

	private Integer policyApplicationId;

	private String status;
	
	private String startDate;  // if status is approved
	
	private String endDate;  // if status is approved

}
