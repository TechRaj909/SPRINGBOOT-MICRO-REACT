package com.cms.policy.dto;

import java.util.ArrayList;
import java.util.List;

import com.cms.policy.entity.Policy;

import lombok.Data;

@Data
public class PolicyResponseDto extends CommonApiResponse {

	private List<Policy> policies = new ArrayList<>();

}
