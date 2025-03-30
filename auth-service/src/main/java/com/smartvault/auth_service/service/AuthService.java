package com.smartvault.auth_service.service;

import com.smartvault.auth_service.model.User;
import com.smartvault.auth_service.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    // ✅ Constructor-based Dependency Injection
    public AuthService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    // ✅ Register User (Hashing PIN before saving)
    public String registerUser(User user) {
        if (userRepository.findByDebitCardNumber(user.getDebitCardNumber()).isPresent()) {
            return "User already exists!";
        }

        user.setPin(user.getPin(), passwordEncoder); // ✅ Hash PIN before saving
        //user.setActive(true);

        userRepository.save(user);
        return "User registered successfully!";
    }

    // ✅ Authenticate User
    public String authenticateUser(String debitCardNumber, String pin) {
        Optional<User> userOptional = userRepository.findByDebitCardNumber(debitCardNumber);

        if (userOptional.isEmpty()) {
            return "Invalid debit card number";
        }

        User user = userOptional.get();

        if (!user.getActive()) {
            return "User is inactive. Access denied.";
        }

        if (!user.checkPin(pin, passwordEncoder)) {
            return "Incorrect PIN";
        }

        return "Login successful!";
    }
}
