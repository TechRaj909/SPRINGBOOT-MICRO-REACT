package com.cms.policy.external;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.cms.policy.dto.UserDetailResponseDto;

@Component
@FeignClient(name = "cms-user-service", url = "http://localhost:9000/api/user")
public interface UserService {
	
	@GetMapping("/fetch")
	UserDetailResponseDto getUserById(@RequestParam("userId") int  userId);
	
}
