package com.cms.policy.entity;

import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

@Entity
@Data
public class CoverageDetails {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	private String type;

	private String description;

	private BigDecimal amount;

	@JsonIgnore
	@ManyToOne
	@JoinColumn(name = "policy_id")
	private Policy policy;

}
