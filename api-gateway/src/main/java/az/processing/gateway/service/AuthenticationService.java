package az.processing.gateway.service;

import az.processing.gateway.dto.security.AfterSignInResponseDto;
import az.processing.gateway.dto.security.JwtRequest;
import az.processing.gateway.dto.security.SignUpDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;


@FeignClient(name = "${registration.serviceid}", configuration = FeignSimpleEncoderConfig.class)
public interface AuthenticationService {

    @PostMapping(value = "/api/auth/register")
    String registerUser(SignUpDto request);


    @PostMapping("/api/auth/authenticate")
    AfterSignInResponseDto authenticateUser(JwtRequest jwtRequest);

}