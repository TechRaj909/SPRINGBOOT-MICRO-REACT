package com.cms.policy.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cms.policy.dao.PolicyApplicationDao;
import com.cms.policy.entity.Policy;
import com.cms.policy.entity.PolicyApplication;

@Service
public class PolicyApplicationServiceImpl implements PolicyApplicationService {

	@Autowired
	private PolicyApplicationDao policyApplicationDao;

	@Override
	public PolicyApplication addApplication(PolicyApplication policyApplication) {
		// TODO Auto-generated method stub
		return policyApplicationDao.save(policyApplication);
	}

	@Override
	public PolicyApplication updateApplication(PolicyApplication policyApplication) {
		// TODO Auto-generated method stub
		return policyApplicationDao.save(policyApplication);
	}

	@Override
	public PolicyApplication getById(int id) {
		Optional<PolicyApplication> optional = this.policyApplicationDao.findById(id);

		if (optional.isPresent()) {
			return optional.get();
		}

		return null;
	}

	@Override
	public List<PolicyApplication> getAllApplication() {
		// TODO Auto-generated method stub
		return policyApplicationDao.findAll();
	}

	@Override
	public List<PolicyApplication> getByCustomerId(int customerId) {
		// TODO Auto-generated method stub
		return policyApplicationDao.findByCustomerId(customerId);
	}

	@Override
	public List<PolicyApplication> getByPolicy(Policy policy) {
		// TODO Auto-generated method stub
		return policyApplicationDao.findByPolicy(policy);
	}

	@Override
	public List<PolicyApplication> getByPolicyAndStatus(Policy policy, String status) {
		// TODO Auto-generated method stub
		return policyApplicationDao.findByPolicyAndStatus(policy, status);
	}

	@Override
	public PolicyApplication getByPolicyAndCustomerId(Policy policy, Integer customerId) {
		// TODO Auto-generated method stub
		return policyApplicationDao.findByPolicyAndCustomerId(policy, customerId);
	}

}
