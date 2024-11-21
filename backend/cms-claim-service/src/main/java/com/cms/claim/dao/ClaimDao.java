package com.cms.claim.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cms.claim.entity.Claim;

@Repository
public interface ClaimDao extends JpaRepository<Claim, Integer>{
	
	List<Claim> findByPolicyApplicationId(Integer applicationId);
	
	List<Claim> findByCustomerId(Integer customerId);
	
	List<Claim> findBySurveyorId(Integer surveyorId);
	
	Claim findByCustomerIdAndPolicyId(Integer customerId, Integer policyId);
	
}
