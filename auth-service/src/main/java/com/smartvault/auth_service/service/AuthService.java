package com.smartvault.auth_service.service;

import com.smartvault.auth_service.config.JwtUtil;
import com.smartvault.auth_service.model.User;
import com.smartvault.auth_service.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    private final JwtUtil jwtUtil;

    public AuthService(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtUtil jwtUtil) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
    }

    // ✅ Register User (Hashing PIN before saving)
    public String registerUser(User user) {
        if (userRepository.findByDebitCardNumber(user.getDebitCardNumber()).isPresent()) {
            return "User already exists!";
        }

        user.setPin(user.getPin(), passwordEncoder); // ✅ Hash PIN before saving
        //user.setActive(true);
        user.updateActiveStatus();

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
        user.updateActiveStatus();

        if (!user.getActive()) {
            return "User is inactive. Access denied.";
        }

        if (!user.checkPin(pin, passwordEncoder)) {
            return "Incorrect PIN";
        }

        return jwtUtil.generateToken(debitCardNumber);
    }
}
