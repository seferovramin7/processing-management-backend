package az.processing.client.security;

import az.processing.client.config.PropertySource;
import az.processing.client.exception.AuthException;
import az.processing.client.util.TokenClaim;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;

@Component
public class AuthProvider implements AuthenticationProvider {

	@Autowired
	private JwtAuthManager jwtAuthManager;

	@Autowired
	private PropertySource propertySource;

	@Override
	public Authentication authenticate(final Authentication authentication) {
		final AuthenticationModel authenticationModel = (AuthenticationModel) authentication;
			doAuthorization(authenticationModel);
			SecurityContextHolder.getContext().setAuthentication(authentication);

			System.out.println("-----------------------------------");

			Authentication authentication1 = SecurityContextHolder.getContext().getAuthentication();
			String email = authentication1.getName();

			System.out.println("-----------------------------------");
			System.out.println(email);
			System.out.println("-----------------------------------");

//			SecurityContextHolder.getContext().setAuthentication(authenticationModel);

		return authenticationModel;
	}

	@Override
	public boolean supports(Class<?> authentication) {
		return AuthenticationModel.class.isAssignableFrom(authentication);
	}

	private final void doAuthorization(final AuthenticationModel appAuthUserModel) {
		final String cleansedToken = validateToken(appAuthUserModel.getCredentials());
		final Jws<Claims> claims = jwtAuthManager.validateToken(cleansedToken);

//		appAuthUserModel.setAuthorities(getAuthorities(claims));
		appAuthUserModel.setCredentials("TBS");
		appAuthUserModel.setDetails(claims.getBody().get(TokenClaim.USER_INFO.getKey(), String.class));
		appAuthUserModel.setName(claims.getBody().get(TokenClaim.SUB.getKey(), String.class));
		appAuthUserModel.setPrincipal(getAuthUserModel(claims));
		appAuthUserModel.setAuthenticated(true);
	}

	private final String validateToken(final String headerValue) {
		if (ObjectUtils.isEmpty(headerValue)) throw new AuthException("Authorization Header is empty");
		
		final String token = headerValue.replace("Bearer", "");
		if (ObjectUtils.isEmpty(token)) throw new AuthException("Token is Empty");
		return token;
	}

	@SuppressWarnings("unchecked")
	private final List<AuthRoleModel> getAuthorities(Jws<Claims> claims) {
		List<String> authorities = claims.getBody().get(TokenClaim.AUTHORITIES.getKey(), List.class);
		return authorities.stream().map(AuthRoleModel::new).collect(Collectors.toList());
	}

	private final AuthUserModel getAuthUserModel(Jws<Claims> claims) {
		final AuthUserModel authUserModel = new AuthUserModel();
		authUserModel.setUserName(claims.getBody().get(TokenClaim.USER_INFO.getKey(), String.class));
		return authUserModel;
	}

}
