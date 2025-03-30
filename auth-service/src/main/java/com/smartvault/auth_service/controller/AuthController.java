package com.smartvault.auth_service.controller;

import com.smartvault.auth_service.model.User;
import com.smartvault.auth_service.service.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;
    private final PasswordEncoder passwordEncoder;

    // ✅ Constructor-based Dependency Injection
    public AuthController(AuthService authService, PasswordEncoder passwordEncoder) {
        this.authService = authService;
        this.passwordEncoder = passwordEncoder;
    }

    // ✅ Register User API
    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody User user) {
        String response = authService.registerUser(user);
        return ResponseEntity.ok(response);
    }

    // ✅ Login User API
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestParam String debitCardNumber, @RequestParam String pin) {
        String response = authService.authenticateUser(debitCardNumber, pin);
        return ResponseEntity.ok(response);
    }
}
