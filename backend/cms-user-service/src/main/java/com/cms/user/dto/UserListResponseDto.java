package com.cms.user.dto;

import java.util.ArrayList;
import java.util.ArrayList;
import java.util.List;

import lombok.Data;

@Data
public class UserListResponseDto extends CommonApiResponse {
	
	private List<UserList> users = new ArrayList<>();

}
