package com.cms.claim.exception;

public class ClaimNotFoundException extends RuntimeException {
	
	public ClaimNotFoundException() {
		super("Claim not found in database");
	}
	
	public ClaimNotFoundException(String message) {
		super(message);
	}

}
