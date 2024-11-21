package com.cms.policy.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cms.policy.dao.CoverageDetailsDao;
import com.cms.policy.entity.CoverageDetails;
import com.cms.policy.entity.Policy;

@Service
public class CoverageDetailsServiceImpl implements CoverageDetailsService {

	@Autowired
	private CoverageDetailsDao coverageDetailsDao;

	@Override
	public CoverageDetails addCoverageDetail(CoverageDetails detail) {
		// TODO Auto-generated method stub
		return coverageDetailsDao.save(detail);
	}

	@Override
	public CoverageDetails getById(int id) {
		Optional<CoverageDetails> optional = this.coverageDetailsDao.findById(id);

		if (optional.isPresent()) {
			return optional.get();
		}

		return null;
	}

	@Override
	public List<CoverageDetails> getByPolicy(Policy policy) {
		// TODO Auto-generated method stub
		return coverageDetailsDao.findByPolicy(policy);
	}

}
