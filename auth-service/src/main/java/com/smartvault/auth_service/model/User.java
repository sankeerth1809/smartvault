package com.smartvault.auth_service.model;

import jakarta.persistence.*;
import lombok.*;
//import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String debitCardNumber;

    private String pin;  // This will store the hashed PIN

    private boolean active;  // Your existing field

    public void setPin(String rawPin) {
        this.pin = new BCryptPasswordEncoder().encode(rawPin); // Hash before storing
    }

    public boolean checkPin(String enteredPin) {
        return new BCryptPasswordEncoder().matches(enteredPin, this.pin); // Verify PIN
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDebitCardNumber() {
        return debitCardNumber;
    }

    public void setDebitCardNumber(String debitCardNumber) {
        this.debitCardNumber = debitCardNumber;
    }

    public String getPin() {
        return pin;
    }

    public boolean getActive() {
        return active;
    }

    public void setActive(boolean ac) {
        active = ac;
    }
}