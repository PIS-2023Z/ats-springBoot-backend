package com.ats.endpoint.offer;


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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class OfferEndpointTest {

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
    void getByFrazeTest() throws Exception {
        this.mockMvc.perform(get("/api/offer/get-by-fraze?word=fghdsf")
                .headers(authHeaders))
                .andExpect(status().isOk());
    }

    @Test
    void shouldNotReturnCheckpoint() throws Exception {
        this.mockMvc.perform(get("/api/offer/get-applied-for")
                .headers(authHeaders))
                .andExpect(status().isOk());
    }

    @Test
    void shouldNotUpdate() throws Exception {
        String requestBody = "{\"email\":\"ats-employee@gmail.com\", \"password\":\"samplePassword123\"}";

        this.mockMvc.perform(put("/api/offer/update")
                        .headers(authHeaders)
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }
}
