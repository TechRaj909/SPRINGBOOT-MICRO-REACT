package com.cms.claim.dto;

import java.math.BigDecimal;
import java.util.List;

import lombok.Data;

@Data
public class Policy {
	
	private int id;

	private String policyId;
	
	private String name;

	private String description;

	private BigDecimal premiumAmount;

	private String plan; // monthly - yearly

	private List<CoverageDetails> coverageDetailsList;

	private String status;

}
