package com.ats.integration.offer;

import com.ats.account.Account;
import com.ats.account.AccountRepository;
import com.ats.offer.Offer;
import com.ats.offer.OfferRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class AccountIntegrationTest {

    @Autowired
    private AccountRepository accountRepository;

    @Test
    public void testFindById() {
        Optional<Account> list = accountRepository.findById(1L);
        assertTrue(list.isPresent());
    }


    @Test
    public void testFindByEmail() {
        Optional<Account> optional = accountRepository.findByEmail("ats-employee@gmail.com");
        assertTrue(optional.isPresent());
        optional = accountRepository.findByEmail("noen fraze");
        assertFalse(optional.isPresent());
    }

}