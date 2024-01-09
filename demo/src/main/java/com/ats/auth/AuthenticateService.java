package com.ats.auth;

import com.ats.account.Account;
import com.ats.account.AccountRepository;
import com.ats.configuration.security.JwtUtils;
import com.ats.globals.Globals;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthenticateService {
/*
    private final AuthenticationManager authenticationManager;
    private final UserDetailsService userDetailsService;
*/

    private final AccountRepository accountRepository;
    private final JwtUtils jwtUtils;

    public ResponseEntity<UserDetails> authenticate(AuthenticateRequest request) {
        Optional<Account> optionalUsername = accountRepository.findByEmail(request.getEmail());
        if (optionalUsername.isEmpty()) {
            HttpHeaders headers = Globals.setHeader("User not found");
            return ResponseEntity.badRequest().headers(headers).body(null);
        }
        Account account = optionalUsername.get();
        //TODO hash
        if (account.getPassword().equals(request.getPassword())) {
            String token = jwtUtils.generateToken(account);
            HttpHeaders headers = Globals.setToken(token);
            return ResponseEntity.ok().headers(headers).body(account);
        } else {
            HttpHeaders headers = Globals.setHeader("Incorrect password");
            return ResponseEntity.badRequest().headers(headers).body(null);
        }
    }
}
