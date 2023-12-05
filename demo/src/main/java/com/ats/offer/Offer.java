package com.ats.offer;

import com.ats.account.Account;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table
@NoArgsConstructor
@Getter
@Setter
@AllArgsConstructor
public class Offer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonIgnore
    @OneToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(referencedColumnName = "id")
    private Account account;

    @Column(nullable = false, length = 50)
    private String name;

    @Column(nullable = false)
    private LocalDateTime publishedDate;
    @Column(nullable = false)
    private LocalDateTime expirationDate;
    @Column(nullable = false)
    private OfferStratus offerStratus;

    private int monthSalary;

    @Column(columnDefinition = "TEXT")
    private String description;


    public Offer(Account account, String name, LocalDateTime publishedDate, LocalDateTime expirationDate, OfferStratus offerStratus) {
        this.account = account;
        this.publishedDate = publishedDate;
        this.expirationDate = expirationDate;
        this.offerStratus = offerStratus;
        this.name = name;
    }
}