package com.ats.auth;

import com.ats.account.Account;
import com.ats.configuration.security.JwtUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/auth/")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticateService authenticateService;

    @PostMapping("authenticate")
    public ResponseEntity<UserDetails> authenticate(
            @RequestBody AuthenticateRequest request
    ) {
        return authenticateService.authenticate(request);
    }

    @PostMapping("create")
    public ResponseEntity create(
            @RequestBody Account account
    ) {
        return authenticateService.create(account);
    }



}
