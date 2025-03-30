package com.smartvault.auth_service.service;

import com.smartvault.auth_service.model.User;
import com.smartvault.auth_service.repository.UserRepository;
//import com.smartvault.auth_service.security.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    public String authenticateUser(String debitCardNumber, String pin) {
        Optional<User> userOptional = userRepository.findByDebitCardNumber(debitCardNumber);

        if (userOptional.isEmpty()) {
            return "Invalid debit card number";
        }

        User user = userOptional.get();

        if (!user.getActive()) {
            return "User is inactive. Access denied.";
        }

        if (!user.getPin().equals(pin)) {
            return "Incorrect PIN";
        }

//        // ✅ Generate JWT Token
//        return jwtUtil.generateToken(user.getDebitCardNumber());
        return debitCardNumber;
    }
}