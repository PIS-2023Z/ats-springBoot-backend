package com.ats.offer;

import com.ats.account.Account;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Offer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonIgnore
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(referencedColumnName = "id")
    private Account account;

    @Column(nullable = false, length = 50)
    private String name;

    @Column(nullable = false)
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
    private LocalDateTime publishedAt;
    @Column(nullable = false)
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
    private LocalDateTime expiresAt;
    @Column(nullable = false)
    private OfferStatus status;

    private int monthlySalary = 0;

    @Column(columnDefinition = "TEXT")
    private String description;

    public Offer(Account account, String name, LocalDateTime publishedAt, LocalDateTime expiresAt, OfferStatus status) {
        this.account = account;
        this.publishedAt = publishedAt;
        this.expiresAt = expiresAt;
        this.status = status;
        this.name = name;
    }
}