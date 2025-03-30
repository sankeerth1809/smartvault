package com.ust.smart_vault.service;

import com.ust.smart_vault.model.Card;
import com.ust.smart_vault.repository.CardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CardService {
    @Autowired
    private CardRepository cardRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public String signup(Card card) {
        card.setAtmPin(passwordEncoder.encode(card.getAtmPin()));
        cardRepository.save(card);
        return "User registered successfully!";
    }
    public Boolean authenticate(String cardNUmber, String atmPin) {
        Card card = cardRepository.findByCardNumber(cardNUmber);
        return card != null && passwordEncoder.matches(atmPin, card.getAtmPin());
    }
}
