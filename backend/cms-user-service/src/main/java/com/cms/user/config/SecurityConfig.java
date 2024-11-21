package com.cms.user.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.cms.user.filter.JwtFilter;
import com.cms.user.service.CustomUserDetailsService;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

	@Autowired
	private CustomUserDetailsService userDetailsService;

	@Autowired
	private JwtFilter jwtFilter;

//    @Override
//    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        auth.userDetailsService(userDetailsService);
//    }

//    @Bean
//    public PasswordEncoder passwordEncoder(){
//        return new BCryptPasswordEncoder();
//    }

//    @Bean(name = BeanIds.AUTHENTICATION_MANAGER)
//    @Override
//    public AuthenticationManager authenticationManagerBean() throws Exception {
//        return super.authenticationManagerBean();
//    }
//
//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//        http
//        .csrf().disable()
//        .authorizeRequests()
//        .anyRequest().permitAll()
////        .antMatchers("/api/user/greeting",
////        		     "/api/location/add",
////        		     "/api/facility/add",
////                     "/api/hotel/add")
////                     .hasAuthority("admin")
//  //      .antMatchers("/greeting").permitAll()
////        .antMatchers("/api/book/hotel/")
////                .hasAuthority("Customer")
//        .and()
//        .exceptionHandling()
//        .and()
//        .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
//        
//        http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
//    }

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
		httpSecurity.csrf().disable().authorizeRequests().anyRequest().permitAll()
//      .antMatchers("/api/user/greeting",
//      		     "/api/location/add",
//      		     "/api/facility/add",
//                   "/api/hotel/add")
//                   .hasAuthority("admin")
//      .antMatchers("/greeting").permitAll()
//      .antMatchers("/api/book/hotel/")
//              .hasAuthority("Customer")
				.and().exceptionHandling().and().sessionManagement()
				.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
				.and()
				.userDetailsService(userDetailsService);
				

		// Add a filter to validate the tokens with every request
		httpSecurity.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
		
		return httpSecurity.build();
	}

	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration)
			throws Exception {
		return authenticationConfiguration.getAuthenticationManager();
	}
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
}
