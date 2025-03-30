package com.ust.smart_vault.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.CsrfConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable()) // Disable CSRF (if needed for APIs)
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/card/signup", "/card/login").permitAll()// Allow unauthenticated access to these endpoints
                        .requestMatchers("/lockers/**").authenticated() // Restrict access to unauthenticated users
                        .anyRequest().authenticated() // Require authentication for all other endpoints
                )
                .httpBasic(httpBasicCustomizer -> {}); // Configure HTTP Basic Authentication

        return http.build();
    }




    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
