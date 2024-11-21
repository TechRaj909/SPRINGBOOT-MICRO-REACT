package com.cms.claim.dto;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class AddClaimRequest {

	private int customerId;
	
	private int policyApplicationId;
	
	private int policyId;
	
	private String accidentDate;
	
	private BigDecimal claimAmount;
	
}
