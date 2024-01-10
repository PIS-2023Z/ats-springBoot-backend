package com.ats.configuration;

import com.ats.account.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@EnableWebSecurity
@Configuration
public class Chain {

    private JwtAthFilter jwtAthFilter;

    private SecurityConfig securityConfig;
    @Autowired
    public void setJwtAthFilter(JwtAthFilter jwtAthFilter) {
        this.jwtAthFilter = jwtAthFilter;
    }

    @Autowired
    public void setSecurityConfig(SecurityConfig securityConfig) {
        this.securityConfig = securityConfig;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeRequests()
                .requestMatchers("api/auth/authenticate")
                .permitAll()
                .anyRequest()
                .authenticated()
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authenticationProvider(securityConfig.authenticationProvider())
                .addFilterBefore(jwtAthFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

}
