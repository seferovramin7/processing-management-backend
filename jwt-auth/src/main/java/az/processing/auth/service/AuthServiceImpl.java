package az.processing.auth.service;

import az.processing.auth.exception.AuthException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import az.processing.auth.config.PropertySource;
import az.processing.auth.model.AuthTokenModel;
import az.processing.auth.repo.DataStore;
import az.processing.auth.utils.JWTHelper;
import az.processing.auth.utils.TokenClaim;



@Service
public class AuthServiceImpl implements AuthService {

	@Autowired
	private DataStore dataStore;

	@Autowired
	private PropertySource propertySource;

	@Override
	public AuthTokenModel validateApiKeyAndGetJwtToken(final String apiKey) {
		try {
			final String userId = validateApiKeyAndGetUserId(apiKey);
			final Map<String, Object> claims = getUserInfo(userId);
			final String jwtTokenValue = JWTHelper.createJWT(claims, propertySource.getAppName(),
					propertySource.getAppAuthSecret(), propertySource.getAppTimeToLive());
			return getTokenModel(jwtTokenValue);
		} catch (Exception e) {
			throw new AuthException("Unauthorized API key : " + apiKey, e);
		}
	}

	private final String validateApiKeyAndGetUserId(final String apiKey) {
		return Optional.ofNullable(dataStore.getUserIdForApikey(apiKey))
				.orElseThrow(() -> new AuthException("InValid API Key"));
	}

	private final Map<String, Object> getUserInfo(final String userId) {
		final Map<String, Object> claims = new HashMap<>();
		final String userInfo = dataStore.getUserInfo(userId);
		final String userRole = dataStore.getUserRole(userId);
		final List<String> authorities = new ArrayList<>();
		authorities.add(userRole);
		claims.put(TokenClaim.USER_ID.getKey(), userId);
		claims.put(TokenClaim.USER_INFO.getKey(), userInfo);
		claims.put(TokenClaim.AUTHORITIES.getKey(), authorities);
		return claims;
	}

	private final AuthTokenModel getTokenModel(final String jwtTokenValue) {
		final AuthTokenModel tokenModel = new AuthTokenModel();
		tokenModel.setType(propertySource.getAppAuthTokenType());
		tokenModel.setToken(jwtTokenValue);
		return tokenModel;
	}

}