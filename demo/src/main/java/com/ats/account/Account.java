package com.ats.account;


import com.ats.application.Application;
import com.ats.offer.Offer;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Entity
@Table
@NoArgsConstructor
@Getter
@Setter
@AllArgsConstructor
public class Account implements UserDetails {

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

    @JsonIgnore
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "account")
    private List<Application> applications;

    @JsonIgnore
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "account")
    private List<Offer> offers;

    public Account(String email, String password, String phone, AccountRole accountRole) {
        this.email = email;
        this.password = password;
        this.phone = phone;
        this.accountRole = accountRole;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getUsername() {
        return getEmail();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }
}
