package com.cms.claim.constants;

public class DatabaseConstant {

	public enum ClaimStatus {
		OPEN("Open"), CLOSE("Close");

		private final String status;

		ClaimStatus(String status) {
			this.status = status;
		}

		public String value() {
			return this.status;
		}
	}
	
	public enum ClaimActionStatus {
		PENDING("Pending"),
		ASSIGNED_TO_SURVEYOR("Assigned to Surveyor"),
		ACCEPTED("Accepted"),
		REJECTED("Rejected");

		private final String status;

		ClaimActionStatus(String status) {
			this.status = status;
		}

		public String value() {
			return this.status;
		}
	}

	// by customer
	public enum ClaimResponseStatus {
		SURVEYOR_REJECTED("Rejected"),PENDING("Pending"), ACCEPT("Accept"), WITHDRAW("Withdraw");

		private final String status;

		ClaimResponseStatus(String status) {
			this.status = status;
		}

		public String value() {
			return this.status;
		}
	}

}
