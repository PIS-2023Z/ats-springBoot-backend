package com.ats.mail;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

@Data
@AllArgsConstructor
public class Mail implements Serializable {

    private String addressee;

    private int minutesValid;

    private MailType mailType;

    private String url;

    private String name;

    private String topic;
}
