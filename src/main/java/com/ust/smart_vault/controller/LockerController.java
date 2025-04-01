package com.ust.smart_vault.controller;

import com.ust.smart_vault.model.Card;
import com.ust.smart_vault.model.Locker;
import com.ust.smart_vault.repository.CardRepository;
import com.ust.smart_vault.service.LockerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/lockers")
public class LockerController {
    @Autowired
    private LockerService lockerService;

    @Autowired
    private CardRepository cardRepository;

    @PostMapping("/create")
    public ResponseEntity<String> createLocker(@RequestBody Locker locker, Authentication authentication) {
        String cardNumber = authentication.getName(); // Get logged-in user's identifier (e.g., card number or username)
        System.out.println(cardNumber);
        // Fetch the Card entity based on the username (or card number)
        Card owner = cardRepository.findByCardNumber(cardNumber); // Ensure this method exists in CardRepository
        System.out.println("Authenticated User: " + cardNumber);
        if (owner == null) {
            return ResponseEntity.status(404).body("Owner not found!");
        }
        // Call the service to create the locker
        String response = lockerService.createLocker(locker, owner);
        return ResponseEntity.ok(response);
    }


     //List lockers for the logged-in user
     @GetMapping("/list")
     public ResponseEntity<List<Locker>> listLockers(@RequestParam Long cardId) {
         List<Locker> lockers = lockerService.getLockersByCardId(cardId);
         return ResponseEntity.ok(lockers);
     }



    // Access a locker (validate locker credentials)
    @GetMapping("/access")
    public ResponseEntity<String> accessLocker(@RequestParam String lockerNumber, @RequestParam String lockerPassword, Authentication authentication) {
        String username = authentication.getName(); // Verify user context
        if(lockerService.accessLocker(lockerNumber, lockerPassword)) {
            return ResponseEntity.ok("Locker access granted!");
        } else {
            return ResponseEntity.status(401).body("Access denied! Invalid locker credentials.");
        }
    }
}
