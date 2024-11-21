package com.cms.user.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import com.cms.user.dto.CommonApiResponse;

@RestControllerAdvice
public class GlobalExceptionHandler {
	
	@ExceptionHandler(UserNotFoundException.class)
	public ResponseEntity<CommonApiResponse> handleUserNotFoundException(UserNotFoundException ex) {
		String responseMessage = ex.getMessage();
		
		CommonApiResponse apiResponse = CommonApiResponse.builder().responseMessage(responseMessage).isSuccess(false).status(HttpStatus.NOT_FOUND).build();
		return new ResponseEntity<CommonApiResponse>(apiResponse, HttpStatus.NOT_FOUND);
		
	}

}
