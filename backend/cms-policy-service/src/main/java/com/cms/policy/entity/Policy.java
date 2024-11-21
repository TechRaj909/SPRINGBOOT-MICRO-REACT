package com.cms.policy.entity;

import java.math.BigDecimal;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import lombok.Data;

@Entity
@Data
public class Policy {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	private String policyId;
	
	private String name;

	private String description;

	private BigDecimal premiumAmount;

	private String plan; // monthly - yearly

	@OneToMany(mappedBy = "policy", cascade = CascadeType.ALL)
	private List<CoverageDetails> coverageDetailsList;

	private String status;

}
