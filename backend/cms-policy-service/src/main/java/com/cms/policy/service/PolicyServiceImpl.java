package com.cms.policy.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cms.policy.dao.PolicyDao;
import com.cms.policy.entity.Policy;

@Service
public class PolicyServiceImpl implements PolicyService {

	@Autowired
	private PolicyDao policyDao;

	@Override
	public Policy addPolicy(Policy policy) {
		// TODO Auto-generated method stub
		return policyDao.save(policy);
	}

	@Override
	public Policy updatePolicy(Policy policy) {
		// TODO Auto-generated method stub
		return policyDao.save(policy);
	}

	@Override
	public Policy getById(int policyId) {
		
		Optional<Policy> optional = this.policyDao.findById(policyId);
		
		if(optional.isPresent()) {
			return optional.get();
		}
		
		return null;
	}

	@Override
	public List<Policy> getAll() {
		// TODO Auto-generated method stub
		return policyDao.findAll();
	}

	@Override
	public List<Policy> getAllByStatus(String status) {
		// TODO Auto-generated method stub
		return policyDao.findByStatus(status);
	}

}
