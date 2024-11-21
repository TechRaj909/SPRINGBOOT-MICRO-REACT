package com.cms.claim.utility;

import java.security.SecureRandom;

public class Helper {
	private static final String ALPHANUMERIC_CAPITAL_LETTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";

	public static String generateClientId(int length) {
		SecureRandom random = new SecureRandom();
		StringBuilder sb = new StringBuilder(length);

		for (int i = 0; i < length; i++) {
			int randomIndex = random.nextInt(ALPHANUMERIC_CAPITAL_LETTERS.length());
			char randomChar = ALPHANUMERIC_CAPITAL_LETTERS.charAt(randomIndex);
			sb.append(randomChar);
		}

		return sb.toString();
	}

}
