package com.smartvault.auth_service.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String debitCardNumber;
    private String pin;  // Hashed PIN will be stored
    private Boolean active = true; // Default to active

    public void setPin(String rawPin, org.springframework.security.crypto.password.PasswordEncoder passwordEncoder) {
        this.pin = passwordEncoder.encode(rawPin); // ✅ Hash PIN correctly
    }

    public boolean checkPin(String enteredPin, org.springframework.security.crypto.password.PasswordEncoder passwordEncoder) {
        return passwordEncoder.matches(enteredPin, this.pin); // ✅ Verify PIN correctly
    }

    public String getPin() {
        return pin;
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

    public boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }
}
