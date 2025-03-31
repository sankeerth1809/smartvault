package com.ust.smart_vault.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.CsrfConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.util.List;

@Configuration
public class SecurityConfig {


    private final CardUserDetailsService cardUserDetailsService;
    private final JwtAuthenticationFilter authFilter;
    public SecurityConfig(CardUserDetailsService cardUserDetailsService, JwtAuthenticationFilter authFilter) {
        this.cardUserDetailsService = cardUserDetailsService;
        this.authFilter = authFilter;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable()) // Disable CSRF (if needed for APIs)
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/card/signup", "/card/login", "/swagger-ui/**", "/swagger-ui.html").permitAll()// Allow unauthenticated access to these endpoints
                        .requestMatchers("/lockers/**").authenticated() // Restrict access to unauthenticated users
                        .anyRequest().authenticated() // Require authentication for all other endpoints
                )
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS) // Enforce stateless session (JWT-based auth)
                )
                .authenticationProvider(authenticationProvider()) // Ensure authentication provider is used
                .addFilterBefore(authFilter, UsernamePasswordAuthenticationFilter.class); // Ensure JWT filter is added

        return http.build();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        return cardUserDetailsService;  // Use CardUserDetailsService instead of CardService
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(cardUserDetailsService);
        provider.setPasswordEncoder(passwordEncoder());
        return provider;
    }

    @Bean
    public AuthenticationManager authenticationManager() {
        return new ProviderManager(List.of(authenticationProvider())); // Use the provider bean
    }


    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
