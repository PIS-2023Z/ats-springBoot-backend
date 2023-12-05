package com.example.demo;

import com.example.demo.account.Account;
import com.example.demo.account.AccountRepository;
import com.example.demo.account.AccountRole;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SampleDataLoader implements CommandLineRunner {
        private final AccountRepository accountRepository;



    @Override
    public void run(String... args) {
        Account account = new Account(
            "ats-employee@gmail.com",
            "samplePassword",
            "+48777666555",
            AccountRole.EMPLOYEE
        );
        accountRepository.save(account);
    }

}
