package com.cms.user.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.cms.user.entity.User;

@Service
public class CustomUserDetailsService implements UserDetailsService {
	
    @Autowired
    private UserService userService;

    @Override
    public UserDetails loadUserByUsername(String emailId) throws UsernameNotFoundException {
    	
    	User user = this.userService.getUserByEmail(emailId);
    	
        return org.springframework.security.core.userdetails.User.withUsername(user.getEmailId()).password(user.getPassword()).authorities(user.getRole()).build();
        
    }
    	
}
