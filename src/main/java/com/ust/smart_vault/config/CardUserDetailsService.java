package com.ust.smart_vault.config;

import com.ust.smart_vault.model.Card;
import com.ust.smart_vault.repository.CardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class CardUserDetailsService implements UserDetailsService {

    @Autowired
    private CardRepository cardRepository;

    @Override
    public UserDetails loadUserByUsername(String cardNumber) throws UsernameNotFoundException {
        Card card = cardRepository.findByCardNumber(cardNumber);
        if (card == null) {
            throw new UsernameNotFoundException("User not found");
        }

        return new User(card.getCardNumber(), card.getAtmPin(), new ArrayList<>());
    }
}