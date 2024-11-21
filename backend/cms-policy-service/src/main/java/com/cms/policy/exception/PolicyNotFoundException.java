package com.cms.policy.exception;

public class PolicyNotFoundException extends RuntimeException {
	
	private static final long serialVersionUID = 1L;

	public PolicyNotFoundException() {
		super("Policy not found in database");
	}
	
	public PolicyNotFoundException(String message) {
		super(message);
	}

}
