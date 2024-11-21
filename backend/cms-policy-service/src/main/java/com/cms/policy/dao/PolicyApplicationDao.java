package com.cms.policy.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cms.policy.entity.Policy;
import com.cms.policy.entity.PolicyApplication;

@Repository 
public interface PolicyApplicationDao extends JpaRepository<PolicyApplication, Integer>{

	List<PolicyApplication> findByPolicy(Policy policy);
	
	List<PolicyApplication> findByPolicyAndStatus(Policy policy, String status);
	
	List<PolicyApplication> findByCustomerId(Integer customerId);
	
	PolicyApplication findByPolicyAndCustomerId(Policy policy, Integer customerId);
	
}
