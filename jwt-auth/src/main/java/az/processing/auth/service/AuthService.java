package az.processing.auth.service;

import az.processing.auth.model.AuthTokenModel;

public interface AuthService {

	public AuthTokenModel validateApiKeyAndGetJwtToken(String apiKey);

}
