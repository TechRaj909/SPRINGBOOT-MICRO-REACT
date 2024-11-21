package com.cms.policy.dto;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class CoverageDetailDto {

	private String type;

	private String description;

	private BigDecimal amount;

}
