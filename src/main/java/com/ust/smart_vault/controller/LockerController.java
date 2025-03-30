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
        String username = authentication.getName(); // Get logged-in user's identifier (e.g., card number or username)
        // Fetch the Card entity based on the username (or card number)
        Card owner = cardRepository.findByCardNumber(username); // Ensure this method exists in CardRepository
        if (owner == null) {
            return ResponseEntity.status(404).body("Owner not found!");
        }
        // Call the service to create the locker
        String response = lockerService.createLocker(locker, owner);
        return ResponseEntity.ok(response);
    }


    // List lockers for the logged-in user
    @GetMapping("/list")
    public ResponseEntity<List<Locker>> listLockers(Authentication authentication) {
        String username = authentication.getName(); // Get logged-in user's identifier
        List<Locker> lockers = lockerService.getLockersByOwner(username);
        return ResponseEntity.ok(lockers);
    }

    // Access a locker (validate locker credentials)
    @PostMapping("/access")
    public ResponseEntity<String> accessLocker(@RequestParam String lockerNumber, @RequestParam String lockerPassword, Authentication authentication) {
        String username = authentication.getName(); // Verify user context
        if(lockerService.accessLocker(lockerNumber, lockerPassword, username)) {
            return ResponseEntity.ok("Locker access granted!");
        } else {
            return ResponseEntity.status(401).body("Access denied! Invalid locker credentials.");
        }
    }
}
