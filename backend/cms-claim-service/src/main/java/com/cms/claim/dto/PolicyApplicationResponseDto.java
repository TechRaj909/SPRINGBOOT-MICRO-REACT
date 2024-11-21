package com.cms.claim.dto;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

@Data
public class PolicyApplicationResponseDto extends CommonApiResponse {

	private List<PolicyApplication> applications = new ArrayList<>();

}
