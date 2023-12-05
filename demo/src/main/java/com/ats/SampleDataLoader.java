package com.ats;

import com.ats.account.Account;
import com.ats.account.AccountRepository;
import com.ats.account.AccountRole;
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

        Account secAccount = new Account(
                "ats-employer@gmail.com",
                "samplePassword",
                "+48777444555",
                AccountRole.EMPLOYER
        );
        accountRepository.save(secAccount);




    }



}
