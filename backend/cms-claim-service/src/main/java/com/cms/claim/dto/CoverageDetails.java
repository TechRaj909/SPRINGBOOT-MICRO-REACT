package com.cms.claim.dto;

import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.Data;

@Data
public class CoverageDetails {

	private int id;

	private String type;

	private String description;

	private BigDecimal amount;

	private Policy policy;

}
