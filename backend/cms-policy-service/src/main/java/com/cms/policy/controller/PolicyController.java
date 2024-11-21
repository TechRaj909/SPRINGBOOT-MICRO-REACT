package com.cms.policy.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cms.policy.dto.AddPolicyApplicationRequest;
import com.cms.policy.dto.AddPolicyRequestDto;
import com.cms.policy.dto.CommonApiResponse;
import com.cms.policy.dto.PolicyApplicationResponseDto;
import com.cms.policy.dto.PolicyResponseDto;
import com.cms.policy.dto.UpdatePolicyApplicationStatusRequest;
import com.cms.policy.resource.PolicyResource;

@RestController
@RequestMapping("/api/policy/")
public class PolicyController {

	@Autowired
	private PolicyResource policyResource;

	@PostMapping("/add")
	public ResponseEntity<CommonApiResponse> addPolicy(@RequestBody AddPolicyRequestDto request) {
		return policyResource.addPolicy(request);
	}

	@GetMapping("/fetch/all")
	public ResponseEntity<PolicyResponseDto> fetchAllPolicies() {
		return policyResource.fetchAllPolicies();
	}

	@GetMapping("/fetch")
	public ResponseEntity<PolicyResponseDto> fetchPolicy(@RequestParam("policyId") Integer policy) {
		return policyResource.fetchPolicy(policy);
	}
	
	@DeleteMapping("/delete")
	public ResponseEntity<CommonApiResponse> deletePolicy(@RequestParam("policyId") Integer policyId) {
		return policyResource.deletePolicy(policyId);
	}

	@PostMapping("/application/add")
	public ResponseEntity<CommonApiResponse> addPolicyApplication(@RequestBody AddPolicyApplicationRequest request) {
		return policyResource.addPolicyApplication(request);
	}

	@GetMapping("/application/fetch/all")
	public ResponseEntity<PolicyApplicationResponseDto> fetchPolicyApplications() {
		return policyResource.fetchPolicyApplications();
	}

	@GetMapping("/application/fetch")
	public ResponseEntity<PolicyApplicationResponseDto> fetchPolicyApplication(
			@RequestParam("applicationId") Integer applicationId) {
		return policyResource.fetchPolicyApplication(applicationId);
	}

	@GetMapping("/application/fetch/customer-wise")
	public ResponseEntity<PolicyApplicationResponseDto> fetchPolicyApplicationByCustomerId(
			@RequestParam("customerId") Integer customerId) {
		return policyResource.fetchPolicyApplicationByCustomerId(customerId);
	}

	@PutMapping("/application/status/update")
	public ResponseEntity<CommonApiResponse> updateApplicationStatus(
			@RequestBody UpdatePolicyApplicationStatusRequest request) {
		return policyResource.updateApplicationStatus(request);
	}

	@GetMapping("/coverage/type/fetch")
	public ResponseEntity<List<String>> fetchPolicyCoverage() {
		return policyResource.fetchPolicyCoverage();
	}
	
	@GetMapping("/application/status/fetch")
	public ResponseEntity<List<String>> fetchApplicationstatus() {
		return policyResource.fetchApplicationstatus();
	}
	
	@GetMapping("/plans/fetch")
	public ResponseEntity<List<String>> fetchPolicyPlans() {
		return policyResource.fetchPolicyPlans();
	}

}
