package com.cms.policy.dto;

import java.util.ArrayList;
import java.util.List;

import com.cms.policy.entity.PolicyApplication;

import lombok.Data;

@Data
public class PolicyApplicationResponseDto extends CommonApiResponse {

	private List<PolicyApplication> applications = new ArrayList<>();

}
