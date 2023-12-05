package com.ats.offer.requests;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateOfferRequest {
    private String name;
    private int daysToExpire;
    private int monthSalary;
    private String description;
}
