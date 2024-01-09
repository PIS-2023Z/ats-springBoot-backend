package com.ats.account;


import com.ats.mail.Mail;
import com.ats.mail.MailType;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/account/")
public class AccountController {

    private final KafkaTemplate kafkaTemplate;

    //only for kafka testing
    @PostMapping("kafka-test")
    public void sendExample() {
        Mail mail = new Mail(
                "piotr.kow.59@gmail.com",
                25,
                MailType.REGISTER,
                "token"
        );
        kafkaTemplate.send("mail", mail);
    }

}
