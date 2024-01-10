package com.ats.auth;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
public class AuthenticateRequest implements Serializable {
    private String email;
    private String password;
}
