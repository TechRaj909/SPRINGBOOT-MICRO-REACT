package com.cms.claim.dto;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class UpdateClaimRequestDto {

	private Integer claimId;

	private Integer surveyorId; // for assigning customer claim to surveyor from admin side

	// ******* from surveyor *********
	private BigDecimal amtApprovedBySurveyor;

	// if Reject --> claimStatus --> Close
	// if Accepted --> claim status -->
	// Open only or no change --> so Customer will decide to accept or withdraw at
	// that time close it
	private String actionStatus; // claim accepted , claim rejected

	// ******* end surveyor *********

	// ******* from customer *********

	private String customerClaimResponse; // accept or withdraw claim

	// ******* end customer *********

}
