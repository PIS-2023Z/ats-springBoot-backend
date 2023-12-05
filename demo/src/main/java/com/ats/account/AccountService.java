package com.ats.account;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AccountService {

    private final AccountRepository accountRepository;

    public Account findById(Long id) {
        return accountRepository.findById(id).orElseThrow();
    }

}
