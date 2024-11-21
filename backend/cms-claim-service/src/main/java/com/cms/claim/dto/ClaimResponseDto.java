package com.cms.claim.dto;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

@Data
public class ClaimResponseDto extends CommonApiResponse {

	private List<ClaimResponse> claims = new ArrayList<>();

}
