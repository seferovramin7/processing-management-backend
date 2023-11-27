package az.processing.gateway.dto.security;

import java.io.Serializable;
import lombok.Data;
import lombok.NoArgsConstructor;

public class JwtResponse implements Serializable {

	private static final long serialVersionUID = -8091879091924046844L;
	private final String jwttoken;

	public JwtResponse(String jwttoken) {
		this.jwttoken = jwttoken;
	}

	public String getToken() {
		return this.jwttoken;
	}

	@Data
	@NoArgsConstructor
	public static class SignUpDto {
		private String email;

		private String password;

		private String role;


		public SignUpDto(String email, String password) {
			this.email = email;
			this.password = password;

		}

	}
}