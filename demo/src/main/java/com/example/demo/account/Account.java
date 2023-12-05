package com.example.demo.account;


import jakarta.persistence.*;
import lombok.*;

@Entity
@Table
@NoArgsConstructor
@Builder
@Getter
@Setter
@AllArgsConstructor
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "email", length = 50, unique = true, nullable = false)
    private String email;
    @Column(name = "password", length = 256, nullable = false)
    private String password;
    @Column(name = "phone", length = 12, unique = true, nullable = false)
    private String phone;
    private boolean locked = false;
    private boolean enabled = true;
    @Column(name = "account_role", nullable = false)
    private AccountRole accountRole;

    public Account(String email, String password, String phone, AccountRole accountRole) {
        this.email = email;
        this.password = password;
        this.phone = phone;
        this.accountRole = accountRole;
    }
}
