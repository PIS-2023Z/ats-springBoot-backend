package com.ats.offer;

import com.ats.account.Account;
import com.ats.application.Application;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.LocalDateTime;
import java.util.List;

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

    @JsonIgnore
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "offer")
    private List<Application> applications;

    public Offer(Account account, String name, LocalDateTime publishedAt, LocalDateTime expiresAt, OfferStatus status) {
        this.account = account;
        this.publishedAt = publishedAt;
        this.expiresAt = expiresAt;
        this.status = status;
        this.name = name;
    }
}