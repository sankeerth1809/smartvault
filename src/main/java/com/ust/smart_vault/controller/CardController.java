package com.ust.smart_vault.controller;

import com.ust.smart_vault.model.Card;
import com.ust.smart_vault.service.CardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/card")
public class CardController {
    @Autowired
    private CardService cardService;

    @PostMapping("/signup")
    public ResponseEntity<String> signup(@RequestBody Card card) {
        String response = cardService.signup(card);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestParam String cardNumber, @RequestParam String atmPin) {
        if (cardService.authenticate(cardNumber, atmPin)) {
            return ResponseEntity.ok("Login successful!");
        } else {
            return ResponseEntity.status(401).body("Invalid credentials.");
        }
    }

}
