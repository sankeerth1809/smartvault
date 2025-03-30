package com.smartvault.auth_service.controller;

import com.smartvault.auth_service.model.User;
import com.smartvault.auth_service.repository.UserRepository;
import com.smartvault.auth_service.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthService authService;
    private final UserRepository userRepository;
    //private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthController(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody User user) {
        if (userRepository.findByDebitCardNumber(user.getDebitCardNumber()).isPresent()) {
            return ResponseEntity.badRequest().body("User already exists with this debit card number");
        }

        // Encrypt PIN before saving
        user.setPin(passwordEncoder.encode(user.getPin()));


        userRepository.save(user);
        return ResponseEntity.ok("User registered successfully");
    }

//    @PostMapping("/login")
//    public Map<String, String> login(@RequestParam String debitCardNumber, @RequestParam String pin) {
//        String token = authService.authenticateUser(debitCardNumber, pin);
//
//        Map<String, String> response = new HashMap<>();
//        response.put("token", token);
//        return response;
//    }

    @PostMapping("/login")
    public String login(@RequestParam String debitCardNumber, @RequestParam String pin) {
        return authService.authenticateUser(debitCardNumber, pin);
    }
}