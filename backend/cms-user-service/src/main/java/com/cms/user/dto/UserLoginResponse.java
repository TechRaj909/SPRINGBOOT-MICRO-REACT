package com.cms.user.dto;

import com.cms.user.entity.User;
import lombok.Data;

@Data
public class UserLoginResponse extends CommonApiResponse {

	private User user;
	
	private String jwtToken;

}
