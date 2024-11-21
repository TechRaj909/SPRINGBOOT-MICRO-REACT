package com.cms.claim.external;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.cms.claim.dto.PolicyApplicationResponseDto;
import com.cms.claim.dto.PolicyResponseDto;

@Component
@FeignClient(name = "cms-policy-service", url = "http://localhost:9000/api/policy")
public interface PolicyService {

	@GetMapping("/fetch/")
	PolicyResponseDto getPolicyById(@RequestParam("policyId") Integer policyId);
	
	@GetMapping("/application/fetch")
	PolicyApplicationResponseDto getPolicyApplicationById(@RequestParam("applicationId") Integer applicationId);

}
