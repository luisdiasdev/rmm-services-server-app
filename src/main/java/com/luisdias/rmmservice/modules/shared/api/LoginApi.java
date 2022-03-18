package com.luisdias.rmmservice.modules.shared.api;

import com.luisdias.rmmservice.infra.security.JwtTokenGenerator;
import com.luisdias.rmmservice.modules.customer.entity.Customer;
import com.luisdias.rmmservice.modules.shared.api.request.LoginRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.tags.Tags;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.luisdias.rmmservice.infra.security.SecurityConstants.AUTH_LOGIN_URL;

@RestController
@RequestMapping(AUTH_LOGIN_URL)
@Tags({@Tag(name = "login"), @Tag(name = "public")})
public class LoginApi {

    private final AuthenticationManager authenticationManager;
    private final JwtTokenGenerator jwtTokenGenerator;

    public LoginApi(AuthenticationManager authenticationManager, JwtTokenGenerator jwtTokenGenerator) {
        this.authenticationManager = authenticationManager;
        this.jwtTokenGenerator = jwtTokenGenerator;
    }

    @PostMapping
    @Operation(security = {})
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginRequest.getUsername(),
                            loginRequest.getPassword()));

            var customer = ((Customer) authentication.getPrincipal());

            var token = jwtTokenGenerator.generateForCustomer(customer);

            return ResponseEntity.ok().header(HttpHeaders.AUTHORIZATION, String.format("Bearer %s", token)).build();
        } catch (BadCredentialsException ex) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
    }
}