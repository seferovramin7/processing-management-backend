package az.processing.msuser.controller;


import az.processing.msuser.config.security.JwtTokenUtil;
import az.processing.msuser.dto.security.AfterSignInResponseDto;
import az.processing.msuser.dto.security.JwtRequest;
import az.processing.msuser.dto.security.SignUpDto;
import az.processing.msuser.entity.UserEntity;
import az.processing.msuser.repository.UserRepository;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin
@SecurityRequirement(name = "Bearer Authentication")
@RequiredArgsConstructor
public class UserController {

    private final UserDetailsService jwtInMemoryUserDetailsService;
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenUtil jwtTokenUtil;


    @RequestMapping(value = "/authenticate", method = RequestMethod.POST)
    public AfterSignInResponseDto signIn(@RequestBody JwtRequest request)
            throws Exception {

        authenticate(request.getEmail(), request.getPassword());
        final UserDetails userDetails = jwtInMemoryUserDetailsService
                .loadUserByUsername(request.getEmail());

        final String token = jwtTokenUtil.generateToken(userDetails);
        return AfterSignInResponseDto.builder()
                .token(token).build();
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public ResponseEntity<?> register(@RequestBody SignUpDto dto) {
        UserEntity entity = userRepository.findUsersEntityByEmail(dto.getEmail());
        if (entity == null) {
            UserEntity userEntity = UserEntity.builder()
                    .email(dto.getEmail())
                    .password(passwordEncoder.encode(dto.getPassword()))
                    .role(dto.getRole())
                    .build();
            userRepository.save(userEntity);
            return ResponseEntity.ok("You signed!");
        } else {
            return ResponseEntity.ok("This account already exist in our DB!");
        }
    }

    @GetMapping("/validate")
    public void validate() {
    }


    private void authenticate(String username, String password) throws Exception {
        Objects.requireNonNull(username);
        Objects.requireNonNull(password);
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(username, password));
        } catch (DisabledException e) {
            throw new Exception("USER_DISABLED", e);
        } catch (BadCredentialsException e) {
            throw new Exception("INVALID_CREDENTIALS", e);
        }
    }
}
