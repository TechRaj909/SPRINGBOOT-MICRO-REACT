package com.cms.policy.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cms.policy.entity.CoverageDetails;
import com.cms.policy.entity.Policy;

@Repository
public interface CoverageDetailsDao extends JpaRepository<CoverageDetails, Integer> {

	List<CoverageDetails> findByPolicy(Policy policy);

}
