package com.cms.policy.constants;

public class DatabaseConstant {

	public static enum ActiveStatus {
		ACTIVE("Active"), IN_ACTIVE("Inactive");

		private String status;

		private ActiveStatus(String status) {
			this.status = status;
		}

		public String value() {
			return this.status;
		}

	}

	public enum PolicyApplicationStatus {
		PENDING("Pending"), APPROVED("Approved"), REJECTED("Rejected");

		private final String status;

		PolicyApplicationStatus(String status) {
			this.status = status;
		}

		public String value() {
			return this.status;
		}
	}

	public enum CoverageType {
		COLLISION("Collision"), COMPREHENSIVE("Comprehensive"), LIABILITY("Liability"),
		UNINSURED_MOTORIST("Uninsured Motorist"), MEDICAL_PAYMENT("Medical Payment"),
		ROAD_SIDE_ASSISTANCE("Roadside Assistance"), RENTAL_REIMBURSEMENT("Rental Reimbursement"),
		GAP_INSURANCE("Gap Insurance");

		private final String type;

		CoverageType(String type) {
			this.type = type;
		}

		public String value() {
			return type;
		}
	}

	public enum PolicyPlan {
		MONTHLY("Monthly"), QUATERLY("Quaterly"), YEARLY("Yearly");

		private final String plan;

		PolicyPlan(String plan) {
			this.plan = plan;
		}

		public String value() {
			return plan;
		}
	}
}
