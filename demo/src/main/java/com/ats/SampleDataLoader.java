package com.ats;

import com.ats.account.Account;
import com.ats.account.AccountRepository;
import com.ats.account.AccountRole;
import com.ats.offer.Offer;
import com.ats.offer.OfferRepository;
import com.ats.offer.OfferStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class SampleDataLoader implements CommandLineRunner {
    private final AccountRepository accountRepository;
    private final OfferRepository offerRepository;


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

        Offer offer = new Offer(
                secAccount,
                "Junior Java Developer",
                LocalDateTime.now(),
                LocalDateTime.now().plusDays(50),
                OfferStatus.GOING
        );
        offer.setMonthlySalary(7000);
        offer.setDescription("HTML, SpringBoot and CSS required");
        offerRepository.save(offer);
    }
}
