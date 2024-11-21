package com.cms.claim.service;

import java.util.List;

import com.cms.claim.entity.Claim;

public interface ClaimService {

	Claim addClaim(Claim claim);

	Claim updateClaim(Claim claim);

	Claim getById(int id);

	List<Claim> getByCustomer(int customerId);

	List<Claim> getBySurveyor(int surveyorId);

	List<Claim> getByPolicyApplicationId(Integer applicationId);
	
	List<Claim> getAll();
	
	Claim getByCustomerIdAndPolicyId(Integer customerId, Integer policyId);

}
