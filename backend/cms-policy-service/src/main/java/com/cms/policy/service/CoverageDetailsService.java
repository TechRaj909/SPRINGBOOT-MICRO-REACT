package com.cms.policy.service;

import java.util.List;

import com.cms.policy.entity.CoverageDetails;
import com.cms.policy.entity.Policy;

public interface CoverageDetailsService {

	CoverageDetails addCoverageDetail(CoverageDetails detail);
	
	CoverageDetails getById(int id);

	List<CoverageDetails> getByPolicy(Policy policy);

}
