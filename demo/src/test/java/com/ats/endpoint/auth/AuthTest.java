package com.ats.endpoint.auth;

import com.ats.account.Account;
import com.ats.account.AccountRole;
import com.ats.configuration.security.JwtUtils;
import com.ats.globals.Globals;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class AuthTest {

    private HttpHeaders authHeaders;

    @Autowired
    private JwtUtils jwtUtils;

    @BeforeAll
    public void setToken() {
        Account account = new Account(
                "ats-employee@gmail.com",
                "samplePassword",
                "+48777666555",
                AccountRole.EMPLOYEE
        );
        String token = jwtUtils.generateToken(account);
        this.authHeaders = Globals.setAuthToken(token);
    }

    @Autowired
    private MockMvc mockMvc;

    @Test
    void shouldLogin() throws Exception {
        String requestBody = "{\"email\":\"ats-employee@gmail.com\", \"password\":\"samplePassword\"}";

        this.mockMvc.perform(post("/api/auth/authenticate")
                        .headers(authHeaders)
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void shouldNotLogin() throws Exception {
        String requestBody = "{\"email\":\"ats-employee@gmail.com\", \"password\":\"samplePassword123\"}";

        this.mockMvc.perform(post("/api/auth/authenticate")
                        .headers(authHeaders)
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    void registerEmailTakenTest() throws Exception {
        String requestBody = "{\"email\":\"ats-employee@gmail.com\", \"password\":\"samplePassword123\"}";

        this.mockMvc.perform(post("/api/auth/create")
                        .headers(authHeaders)
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    void registerEmailInvalidTest() throws Exception {
        String requestBody = "{\"email\":\"ats-employe\", \"password\":\"samplePassword123\"}";

        this.mockMvc.perform(post("/api/auth/create")
                        .headers(authHeaders)
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    void registerRoleTest() throws Exception {
        String requestBody = "{\"email\":\"ats-employe@gmail.com\", \"password\":\"samplePassword123\", \"accountRole\":\"EME\"}";

        this.mockMvc.perform(post("/api/auth/create")
                        .headers(authHeaders)
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }
}
