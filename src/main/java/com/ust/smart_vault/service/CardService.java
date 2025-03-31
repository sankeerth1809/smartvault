package com.ust.smart_vault.service;

import com.ust.smart_vault.config.JwtUtil;
import com.ust.smart_vault.model.Card;
import com.ust.smart_vault.repository.CardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Optional;

@Service
public class CardService {
    @Autowired
    private CardRepository cardRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtil jwtUtil;

    public String signup(Card card) {
        card.setAtmPin(passwordEncoder.encode(card.getAtmPin()));
        cardRepository.save(card);
        return "User registered successfully!";
    }
    public String login(String cardNumber, String atmPin) {
        Card card = cardRepository.findByCardNumber(cardNumber);
        if (card != null && passwordEncoder.matches(atmPin, card.getAtmPin())) {
            return jwtUtil.generateToken(cardNumber);
        }
        return null;
    }
}
