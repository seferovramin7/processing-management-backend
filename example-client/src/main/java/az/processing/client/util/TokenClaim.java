package az.processing.client.util;

public enum TokenClaim {

	SUB("sub"),
	USER_INFO("UserInfo"),
	AUTHORITIES("authorities");

	private String key;

	private TokenClaim(final String key) {
		this.key = key;
	}

	public String getKey() {
		return key;
	}

}
