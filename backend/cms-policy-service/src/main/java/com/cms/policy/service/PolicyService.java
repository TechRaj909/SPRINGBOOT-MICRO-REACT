package com.cms.policy.service;

import java.util.List;

import com.cms.policy.entity.Policy;

public interface PolicyService {
	
	Policy addPolicy(Policy policy);
	
	Policy updatePolicy(Policy policy);
	
	Policy getById(int policyId);
	
	List<Policy> getAll();
	
	List<Policy> getAllByStatus(String status);

}
