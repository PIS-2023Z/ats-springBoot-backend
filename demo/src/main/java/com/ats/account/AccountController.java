package com.ats.account;


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
        kafkaTemplate.send("mail", "sent from endpoint");
    }

}
