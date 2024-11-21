package com.cms.claim.resource;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import com.cms.claim.constants.DatabaseConstant.ClaimActionStatus;
import com.cms.claim.constants.DatabaseConstant.ClaimResponseStatus;
import com.cms.claim.constants.DatabaseConstant.ClaimStatus;
import com.cms.claim.dto.AddClaimRequest;
import com.cms.claim.dto.ClaimResponse;
import com.cms.claim.dto.ClaimResponseDto;
import com.cms.claim.dto.CommonApiResponse;
import com.cms.claim.dto.Policy;
import com.cms.claim.dto.PolicyApplication;
import com.cms.claim.dto.PolicyApplicationResponseDto;
import com.cms.claim.dto.PolicyResponseDto;
import com.cms.claim.dto.UpdateClaimRequestDto;
import com.cms.claim.dto.User;
import com.cms.claim.dto.UserDetailResponseDto;
import com.cms.claim.entity.Claim;
import com.cms.claim.external.PolicyService;
import com.cms.claim.external.UserService;
import com.cms.claim.service.ClaimService;
import com.cms.claim.utility.Helper;

@Component
public class ClaimResource {

	@Autowired
	private ClaimService claimService;
	
	@Autowired
	private PolicyService policyService;
	
	@Autowired
	private UserService userService;

	public ResponseEntity<CommonApiResponse> addClaim(AddClaimRequest request) {

		CommonApiResponse response = new CommonApiResponse();

		if (request == null) {
			response.setResponseMessage("bad request- missing or invalid request");
			response.setSuccess(false);

			return new ResponseEntity<CommonApiResponse>(response, HttpStatus.BAD_REQUEST);
		}

		if (request.getPolicyId() == 0 || request.getPolicyApplicationId() == 0 || request.getCustomerId() == 0
				|| request.getClaimAmount() == null || request.getAccidentDate() == null) {
			response.setResponseMessage("bad request- missing or invalid request");
			response.setSuccess(false);

			return new ResponseEntity<CommonApiResponse>(response, HttpStatus.BAD_REQUEST);
		}
		
		PolicyResponseDto policyRes = this.policyService.getPolicyById(request.getPolicyId());

		if(policyRes == null) {
			response.setResponseMessage("some of service is down, internal error");
			response.setSuccess(false);

			return new ResponseEntity<CommonApiResponse>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		List<Policy> policies = policyRes.getPolicies();
		
		if(CollectionUtils.isEmpty(policies)) {
			response.setResponseMessage("Policy not found!!!");
			response.setSuccess(false);

			return new ResponseEntity<CommonApiResponse>(response, HttpStatus.BAD_REQUEST);
		}
		
		PolicyApplicationResponseDto applicationRes = this.policyService.getPolicyApplicationById(request.getPolicyApplicationId());

		if(applicationRes == null) {
			response.setResponseMessage("some of service is down, internal error");
			response.setSuccess(false);

			return new ResponseEntity<CommonApiResponse>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		List<PolicyApplication> applications = applicationRes.getApplications();
		
		if(CollectionUtils.isEmpty(applications)) {
			response.setResponseMessage("Policy Application not found!!!");
			response.setSuccess(false);

			return new ResponseEntity<CommonApiResponse>(response, HttpStatus.BAD_REQUEST);
		}
		
		UserDetailResponseDto userresponse = this.userService.getUserById(request.getCustomerId());

		if(userresponse == null) {
			response.setResponseMessage("some of service is down, internal error");
			response.setSuccess(false);

			return new ResponseEntity<CommonApiResponse>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		User user = userresponse.getUser();
		
		if(user == null) {
			response.setResponseMessage("user not found!!!");
			response.setSuccess(false);

			return new ResponseEntity<CommonApiResponse>(response, HttpStatus.BAD_REQUEST);
		}
		
		Claim existingClaim = this.claimService.getByCustomerIdAndPolicyId(request.getCustomerId(), request.getPolicyId());
		
		if(existingClaim != null) {
			response.setResponseMessage("You have already claimed for this policy");
			response.setSuccess(false);

			return new ResponseEntity<CommonApiResponse>(response, HttpStatus.BAD_REQUEST);
		}
		
		Claim claim = new Claim();
		claim.setActionStatus(ClaimActionStatus.PENDING.value());
		claim.setAmtApprovedBySurveyor(BigDecimal.ZERO);
		claim.setClaimAmount(request.getClaimAmount());
		claim.setClaimApplicationDate(String.valueOf(LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant().toEpochMilli()));
		claim.setClaimId(Helper.generateClientId(10));
		claim.setClaimStatus(ClaimStatus.OPEN.value());
		claim.setCustomerClaimResponse(ClaimResponseStatus.PENDING.value());
		claim.setCustomerId(request.getCustomerId());
		claim.setDateOfAccident(request.getAccidentDate());
		claim.setPolicyApplicationId(request.getPolicyApplicationId());
		claim.setPolicyId(request.getPolicyId());
		
		Claim addedClaim = this.claimService.addClaim(claim);
		
		if(addedClaim == null) {
			response.setResponseMessage("Fail to add the customer claim!!!");
			response.setSuccess(false);

			return new ResponseEntity<CommonApiResponse>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		response.setResponseMessage("Claim added successful!!!");
		response.setSuccess(true);

		return new ResponseEntity<CommonApiResponse>(response, HttpStatus.OK);
	}

	public ResponseEntity<ClaimResponseDto> fetchAllClaims() {
		
		ClaimResponseDto response = new ClaimResponseDto();
		
		List<Claim> claims = this.claimService.getAll();
		
		if(CollectionUtils.isEmpty(claims)) {
			response.setResponseMessage("No Claims found!!!");
			response.setSuccess(false);

			return new ResponseEntity<ClaimResponseDto>(response, HttpStatus.OK);
		}
		
		List<ClaimResponse> claimResponse = new ArrayList<>();
		
		for(Claim claim : claims) {
			
			ClaimResponse claimRes = new ClaimResponse();
			claimRes.setClaim(claim);
			
			PolicyResponseDto policyRes = this.policyService.getPolicyById(claim.getPolicyId());

			if(policyRes != null) {
				List<Policy> policies = policyRes.getPolicies();
				
				if(!CollectionUtils.isEmpty(policies)) {
					claimRes.setPolicy(policies.get(0));
				}
			}
			
			PolicyApplicationResponseDto applicationRes = this.policyService.getPolicyApplicationById(claim.getPolicyApplicationId());

			if(applicationRes != null) {
				List<PolicyApplication> applications = applicationRes.getApplications();
				if(!CollectionUtils.isEmpty(applications)) {
					claimRes.setApplication(applications.get(0));
				}
			}
			
			UserDetailResponseDto userresponse = this.userService.getUserById(claim.getCustomerId());

			if(userresponse != null) {
				User user = userresponse.getUser();
				if(user != null) {
					claimRes.setCustomer(user);
				}
			}
			
			if(claim.getSurveyorId() != 0) {
				UserDetailResponseDto userres = this.userService.getUserById(claim.getSurveyorId());

				if(userres != null) {
					User user = userres.getUser();
					if(user != null) {
						claimRes.setSurveyor(user);
					}
				}
			}
			
			claimResponse.add(claimRes);
			
		}
		
		response.setClaims(claimResponse);
		response.setResponseMessage("Claims Fetched Successful!!");
		response.setSuccess(true);

		return new ResponseEntity<ClaimResponseDto>(response, HttpStatus.OK);
	}

	public ResponseEntity<ClaimResponseDto> fetchAllClaimsByCustomer(Integer customerId) {
		
		ClaimResponseDto response = new ClaimResponseDto();
		
		if(customerId == null) {
			response.setResponseMessage("Customer ID missing");
			response.setSuccess(false);
			return new ResponseEntity<ClaimResponseDto>(response, HttpStatus.BAD_REQUEST);
		}
		
		List<Claim> claims = this.claimService.getByCustomer(customerId);
		
		if(CollectionUtils.isEmpty(claims)) {
			response.setResponseMessage("No Claims found!!!");
			response.setSuccess(false);

			return new ResponseEntity<ClaimResponseDto>(response, HttpStatus.OK);
		}
		
		List<ClaimResponse> claimResponse = new ArrayList<>();
		
		for(Claim claim : claims) {
			
			ClaimResponse claimRes = new ClaimResponse();
			claimRes.setClaim(claim);
			
			PolicyResponseDto policyRes = this.policyService.getPolicyById(claim.getPolicyId());

			if(policyRes != null) {
				List<Policy> policies = policyRes.getPolicies();
				
				if(!CollectionUtils.isEmpty(policies)) {
					claimRes.setPolicy(policies.get(0));
				}
			}
			
			PolicyApplicationResponseDto applicationRes = this.policyService.getPolicyApplicationById(claim.getPolicyApplicationId());

			if(applicationRes != null) {
				List<PolicyApplication> applications = applicationRes.getApplications();
				if(!CollectionUtils.isEmpty(applications)) {
					claimRes.setApplication(applications.get(0));
				}
			}
			
			UserDetailResponseDto userresponse = this.userService.getUserById(claim.getCustomerId());

			if(userresponse != null) {
				User user = userresponse.getUser();
				if(user != null) {
					claimRes.setCustomer(user);
				}
			}
			
			if(claim.getSurveyorId() != 0) {
				UserDetailResponseDto userres = this.userService.getUserById(claim.getSurveyorId());

				if(userres != null) {
					User user = userres.getUser();
					if(user != null) {
						claimRes.setSurveyor(user);
					}
				}
			}
			
			claimResponse.add(claimRes);
			
		}
		
		response.setClaims(claimResponse);
		response.setResponseMessage("Claims Fetched Successful!!");
		response.setSuccess(true);

		return new ResponseEntity<ClaimResponseDto>(response, HttpStatus.OK);
	}

	public ResponseEntity<ClaimResponseDto> fetchAllClaimsBySurveyor(Integer surveyorId) {
		
		ClaimResponseDto response = new ClaimResponseDto();
		
		if(surveyorId == null) {
			response.setResponseMessage("Surveyor ID missing");
			response.setSuccess(false);
			return new ResponseEntity<ClaimResponseDto>(response, HttpStatus.BAD_REQUEST);
		}
		
		List<Claim> claims = this.claimService.getBySurveyor(surveyorId);
		
		if(CollectionUtils.isEmpty(claims)) {
			response.setResponseMessage("No Claims found!!!");
			response.setSuccess(false);

			return new ResponseEntity<ClaimResponseDto>(response, HttpStatus.OK);
		}
		
		List<ClaimResponse> claimResponse = new ArrayList<>();
		
		for(Claim claim : claims) {
			
			ClaimResponse claimRes = new ClaimResponse();
			claimRes.setClaim(claim);
			
			PolicyResponseDto policyRes = this.policyService.getPolicyById(claim.getPolicyId());

			if(policyRes != null) {
				List<Policy> policies = policyRes.getPolicies();
				
				if(!CollectionUtils.isEmpty(policies)) {
					claimRes.setPolicy(policies.get(0));
				}
			}
			
			PolicyApplicationResponseDto applicationRes = this.policyService.getPolicyApplicationById(claim.getPolicyApplicationId());

			if(applicationRes != null) {
				List<PolicyApplication> applications = applicationRes.getApplications();
				if(!CollectionUtils.isEmpty(applications)) {
					claimRes.setApplication(applications.get(0));
				}
			}
			
			UserDetailResponseDto userresponse = this.userService.getUserById(claim.getCustomerId());

			if(userresponse != null) {
				User user = userresponse.getUser();
				if(user != null) {
					claimRes.setCustomer(user);
				}
			}
			
			if(claim.getSurveyorId() != 0) {
				UserDetailResponseDto userres = this.userService.getUserById(claim.getSurveyorId());

				if(userres != null) {
					User user = userres.getUser();
					if(user != null) {
						claimRes.setSurveyor(user);
					}
				}
			}
			
			claimResponse.add(claimRes);
			
		}
		
		response.setClaims(claimResponse);
		response.setResponseMessage("Claims Fetched Successful!!");
		response.setSuccess(true);

		return new ResponseEntity<ClaimResponseDto>(response, HttpStatus.OK);
	}

	public ResponseEntity<CommonApiResponse> assignSurveyorForClaim(UpdateClaimRequestDto request) {
		
		CommonApiResponse response = new CommonApiResponse();
		
		if(request == null) {
			response.setResponseMessage("bad request - missing input");
			response.setSuccess(false);
			return new ResponseEntity<CommonApiResponse>(response, HttpStatus.BAD_REQUEST);
		}
		
		if(request.getClaimId() == null || request.getSurveyorId() == null) {
			response.setResponseMessage("bad request - claim id missing");
			response.setSuccess(false);
			return new ResponseEntity<CommonApiResponse>(response, HttpStatus.BAD_REQUEST);
		}
		
		Claim claim = this.claimService.getById(request.getClaimId());
		
		if(claim == null) {
			response.setResponseMessage("bad request - claim not found");
			response.setSuccess(false);
			return new ResponseEntity<CommonApiResponse>(response, HttpStatus.BAD_REQUEST);
		}

		UserDetailResponseDto userres = this.userService.getUserById(request.getSurveyorId());

		User surveyor = null;
		
		if(userres != null) {
		   surveyor = userres.getUser();
		}
		
		claim.setSurveyorId(surveyor.getId());
		claim.setActionStatus(ClaimActionStatus.ASSIGNED_TO_SURVEYOR.value());
		
		Claim updatedClaim = this.claimService.updateClaim(claim);
		
		if(updatedClaim == null) {
			response.setResponseMessage("failed to Assign the surveyor for customer claim");
			response.setSuccess(false);
			return new ResponseEntity<CommonApiResponse>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		response.setResponseMessage("Successfully Assigned Surveyor to Customer Claim!!!");
		response.setSuccess(true);
		return new ResponseEntity<CommonApiResponse>(response, HttpStatus.OK);
	}

	public ResponseEntity<CommonApiResponse> updateClaimBySurveyor(UpdateClaimRequestDto request) {
		
		CommonApiResponse response = new CommonApiResponse();
		
		if(request == null) {
			response.setResponseMessage("bad request - missing input");
			response.setSuccess(false);
			return new ResponseEntity<CommonApiResponse>(response, HttpStatus.BAD_REQUEST);
		}
		
		if(request.getClaimId() == null || request.getActionStatus() == null) {
			response.setResponseMessage("bad request - missing input");
			response.setSuccess(false);
			return new ResponseEntity<CommonApiResponse>(response, HttpStatus.BAD_REQUEST);
		}
		
		if(request.getActionStatus().equals(ClaimActionStatus.ACCEPTED.value()) && request.getAmtApprovedBySurveyor() == null) {
			response.setResponseMessage("bad request - approved amount missing");
			response.setSuccess(false);
			return new ResponseEntity<CommonApiResponse>(response, HttpStatus.BAD_REQUEST);
		}
		
		Claim claim = this.claimService.getById(request.getClaimId());
		
		if(claim == null) {
			response.setResponseMessage("bad request - claim not found");
			response.setSuccess(false);
			return new ResponseEntity<CommonApiResponse>(response, HttpStatus.BAD_REQUEST);
		}
		
		
		claim.setActionStatus(request.getActionStatus());
		
		if(request.getActionStatus().equals(ClaimActionStatus.ACCEPTED.value())) {
			claim.setAmtApprovedBySurveyor(request.getAmtApprovedBySurveyor());
		} else {
			claim.setClaimStatus(ClaimStatus.CLOSE.value()); // because surveyor has Rejected the Claim
			claim.setCustomerClaimResponse(ClaimResponseStatus.SURVEYOR_REJECTED.value());
		}
		
		Claim updatedClaim = this.claimService.updateClaim(claim);
		
		if(updatedClaim == null) {
			response.setResponseMessage("failed to update the claim status");
			response.setSuccess(false);
			return new ResponseEntity<CommonApiResponse>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		response.setResponseMessage("Successfully Updated the Customer Claim!!!");
		response.setSuccess(true);
		return new ResponseEntity<CommonApiResponse>(response, HttpStatus.OK);
	}

	public ResponseEntity<CommonApiResponse> customerClaimResponse(UpdateClaimRequestDto request) {
		
		CommonApiResponse response = new CommonApiResponse();
		
		if(request == null) {
			response.setResponseMessage("bad request - missing input");
			response.setSuccess(false);
			return new ResponseEntity<CommonApiResponse>(response, HttpStatus.BAD_REQUEST);
		}
		
		if(request.getClaimId() == null || request.getCustomerClaimResponse() == null) {
			response.setResponseMessage("bad request - missing input");
			response.setSuccess(false);
			return new ResponseEntity<CommonApiResponse>(response, HttpStatus.BAD_REQUEST);
		}
		
		Claim claim = this.claimService.getById(request.getClaimId());
		
		if(claim == null) {
			response.setResponseMessage("bad request - claim not found");
			response.setSuccess(false);
			return new ResponseEntity<CommonApiResponse>(response, HttpStatus.BAD_REQUEST);
		}
		
		
		claim.setCustomerClaimResponse(request.getCustomerClaimResponse());
		claim.setClaimStatus(ClaimStatus.CLOSE.value());
		
		Claim updatedClaim = this.claimService.updateClaim(claim);
		
		if(updatedClaim == null) {
			response.setResponseMessage("Failed to update the Customer Claim");
			response.setSuccess(false);
			return new ResponseEntity<CommonApiResponse>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		response.setResponseMessage("Successfully Updated the Customer Claim!!!");
		response.setSuccess(true);
		return new ResponseEntity<CommonApiResponse>(response, HttpStatus.OK);
	}
	
}
