package com.cms.user.constants;

public class DatabaseConstant {
	
	public static enum UserStatus {
		ACTIVE(1),
		NOT_ACTIVE(0);
		
		private int status;

	    private UserStatus(int status) {
	      this.status = status;
	    }

	    public int value() {
	      return this.status;
	    }
	     
	}
	
	public static enum UserRole {
		COMPANY("admin"),
		SURVEYOR("surveyor"),
		CUSTOMER("customer"),
		IRDA("irda");   //Insurance Regulatory and Developmenet Authority
		
		private String role;

	    private UserRole(String role) {
	      this.role = role;
	    }

	    public String value() {
	      return this.role;
	    }
	     
	}

}
