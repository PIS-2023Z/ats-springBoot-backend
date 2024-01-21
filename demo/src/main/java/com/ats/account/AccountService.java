package com.ats.account;

import com.ats.configuration.security.JwtUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AccountService {

    private final AccountRepository accountRepository;

    private final JwtUtils jwtUtils;

    public Account findById(Long id) {
        return accountRepository.findById(id).orElseThrow();
    }

    public Account getAccountFromToken(String token) {
        String username = jwtUtils.extractUsernameFromHeader(token);
        return accountRepository.findByEmail(username).orElseThrow();
    }

}
