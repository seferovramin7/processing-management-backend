package az.processing.gateway.controller;

import az.processing.gateway.dto.security.AfterSignInResponseDto;
import az.processing.gateway.dto.security.JwtRequest;
import az.processing.gateway.dto.security.SignUpDto;
import az.processing.gateway.model.AuthTokenModel;
import az.processing.gateway.service.AuthService;
import az.processing.gateway.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RefreshScope
@RestController
@RequiredArgsConstructor
@RequestMapping("/fallback")
public class HystrixController {

    private final AuthService authService;

    private final AuthenticationService authenticationService;

    @GetMapping("/first")
    public String firstServiceFallback() {
        return "This is a fallback for first service.";
    }

    @GetMapping("/second")
    public String secondServiceFallback() {
        return "Second Server overloaded! Please try after some time.";
    }

    @GetMapping("/test")
    public AuthTokenModel testServiceFallback() {
        return authService.getJWTToken("1231");
    }

    @PostMapping("/register")
    public String register(@RequestBody SignUpDto request) {
        return authenticationService.registerUser(request);
    }

    @PostMapping("/authenticate")
    public AfterSignInResponseDto authenticate(@RequestBody JwtRequest request) {
        return authenticationService.authenticateUser(request);
    }

}