package com.cms.claim.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cms.claim.dao.ClaimDao;
import com.cms.claim.entity.Claim;

@Service
public class ClaimServiceImpl implements ClaimService {

	@Autowired
	private ClaimDao claimDao;

	@Override
	public Claim addClaim(Claim claim) {
		// TODO Auto-generated method stub
		return claimDao.save(claim);
	}

	@Override
	public Claim updateClaim(Claim claim) {
		// TODO Auto-generated method stub
		return claimDao.save(claim);
	}

	@Override
	public Claim getById(int id) {
		Optional<Claim> optional = this.claimDao.findById(id);

		if (optional.isPresent()) {
			return optional.get();
		}

		return null;
	}

	@Override
	public List<Claim> getByCustomer(int customerId) {
		// TODO Auto-generated method stub
		return claimDao.findByCustomerId(customerId);
	}

	@Override
	public List<Claim> getBySurveyor(int surveyorId) {
		// TODO Auto-generated method stub
		return claimDao.findBySurveyorId(surveyorId);
	}

	@Override
	public List<Claim> getByPolicyApplicationId(Integer applicationId) {
		// TODO Auto-generated method stub
		return claimDao.findByPolicyApplicationId(applicationId);
	}

	@Override
	public List<Claim> getAll() {
		// TODO Auto-generated method stub
		return claimDao.findAll();
	}

	@Override
	public Claim getByCustomerIdAndPolicyId(Integer customerId, Integer policyId) {
		// TODO Auto-generated method stub
		return claimDao.findByCustomerIdAndPolicyId(customerId, policyId);
	}

}
