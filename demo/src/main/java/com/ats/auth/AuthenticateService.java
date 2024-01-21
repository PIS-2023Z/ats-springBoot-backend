package com.ats.auth;

import com.ats.account.Account;
import com.ats.account.AccountRepository;
import com.ats.account.AccountRole;
import com.ats.configuration.security.JwtUtils;
import com.ats.globals.Globals;
import com.ats.mail.Mail;
import com.ats.mail.MailType;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    private final KafkaTemplate kafkaTemplate;

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

    @Transactional
    public ResponseEntity<Account> create(Account account) {
        if (!Globals.isValidEmail(account.getEmail())) {
            HttpHeaders headers = Globals.setHeader("Email invalid or taken");
            return ResponseEntity.badRequest().headers(headers).body(null);
        }
        Optional<Account> optionalAccountEmail = accountRepository.findByEmail(account.getEmail());
        if(optionalAccountEmail.isEmpty()) {
            HttpHeaders headers = Globals.setHeader("Username taken");
            return ResponseEntity.badRequest().headers(headers).body(null);
        }
        if(!account.getAccountRole().equals(AccountRole.EMPLOYEE) && !account.getAccountRole().equals(AccountRole.EMPLOYER)) {
            HttpHeaders headers = Globals.setHeader("role must be employee or employer");
            return ResponseEntity.badRequest().headers(headers).body(null);
        }
        Account newAccount = null;
        try {
            newAccount = new Account(
                    account.getEmail(),
                    account.getPassword(),
                    account.getPhone(),
                    account.getAccountRole()
            );
            accountRepository.save(newAccount);
        } catch (Exception e) {
            HttpHeaders headers = Globals.setHeader("Error while saving account");
            return ResponseEntity.badRequest().headers(headers).body(null);
        }
        Mail mail = new Mail(
                account.getEmail(),
                25,
                MailType.REGISTER,
                Globals.PRODUCTION_ENABLE_URL + Globals.generateCode(), //TODO save code to db
                "User",
                "Welcome to PIS-ATS"
        );
        kafkaTemplate.send("mail", mail);
        return ResponseEntity.ok(newAccount);
    }
}
