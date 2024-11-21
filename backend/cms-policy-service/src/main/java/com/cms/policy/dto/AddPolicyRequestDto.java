package com.cms.policy.dto;

import java.math.BigDecimal;
import java.util.List;

import lombok.Data;

@Data
public class AddPolicyRequestDto {

	private String name;

	private String description;

	private BigDecimal premiumAmount;

	private String plan;

	private List<CoverageDetailDto> coverageDetails;

}
