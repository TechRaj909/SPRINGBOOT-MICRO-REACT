package com.cms.user.dto;

import com.cms.user.entity.User;

import lombok.Data;

@Data
public class UserDetailResponseDto extends CommonApiResponse {

	private User user;


}
