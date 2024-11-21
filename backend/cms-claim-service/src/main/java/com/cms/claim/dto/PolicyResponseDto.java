package com.cms.claim.dto;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

@Data
public class PolicyResponseDto extends CommonApiResponse {

	private List<Policy> policies = new ArrayList<>();

}
