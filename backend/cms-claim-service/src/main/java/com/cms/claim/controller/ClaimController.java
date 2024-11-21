package com.cms.claim.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cms.claim.dto.AddClaimRequest;
import com.cms.claim.dto.ClaimResponseDto;
import com.cms.claim.dto.CommonApiResponse;
import com.cms.claim.dto.UpdateClaimRequestDto;
import com.cms.claim.resource.ClaimResource;

@RestController
@RequestMapping("/api/claim/")
public class ClaimController {

	@Autowired
	private ClaimResource claimResource;

	@PostMapping("/add")
	public ResponseEntity<CommonApiResponse> addClaim(@RequestBody AddClaimRequest request) {
		return claimResource.addClaim(request);
	}

	@GetMapping("/fetch/all")
	public ResponseEntity<ClaimResponseDto> fetchAllClaims() {
		return claimResource.fetchAllClaims();
	}
	
	@GetMapping("/fetch/customer-wise")
	public ResponseEntity<ClaimResponseDto> fetchAllClaimsByCustomer(@RequestParam("customerId") Integer customerId) {
		return claimResource.fetchAllClaimsByCustomer(customerId);
	}
	
	@GetMapping("/fetch/surveyor-wise")
	public ResponseEntity<ClaimResponseDto> fetchAllClaimsBySurveyor(@RequestParam("surveyorId") Integer surveyorId) {
		return claimResource.fetchAllClaimsBySurveyor(surveyorId);
	}
	
	@PutMapping("/assign/surveyor")
	public ResponseEntity<CommonApiResponse> assignSurveyorForClaim(@RequestBody UpdateClaimRequestDto request) {
		return claimResource.assignSurveyorForClaim(request);
	}
	
	@PutMapping("/surveyor/update")
	public ResponseEntity<CommonApiResponse> updateClaimBySurveyor(@RequestBody UpdateClaimRequestDto request) {
		return claimResource.updateClaimBySurveyor(request);
	}
	
	@PutMapping("/customer/response")
	public ResponseEntity<CommonApiResponse> customerClaimResponse(@RequestBody UpdateClaimRequestDto request) {
		return claimResource.customerClaimResponse(request);
	}
	
}
