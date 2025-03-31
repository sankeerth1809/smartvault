package com.smartvault.locker_service.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "locker")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Locker {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String debitCardNumber; // User's Debit Card Number
    private String lockerNumber; // Locker Number Assigned
    private String lockerPassword;
    private LocalDateTime startTime; // When Locker Was Assigned
    private LocalDateTime endTime; // When Locker Was Released
    private double hourlyRate; // Cost Per Hour
    private double totalCost; // Final Cost
    private boolean isLocked = true;

    public void setLockerPassword(String rawPin, org.springframework.security.crypto.password.PasswordEncoder passwordEncoder) {
        this.lockerPassword = passwordEncoder.encode(rawPin); // ✅ Hash PIN correctly
    }

    public boolean checkPin(String enteredPin, org.springframework.security.crypto.password.PasswordEncoder passwordEncoder) {
        return passwordEncoder.matches(enteredPin, this.lockerPassword); // ✅ Verify PIN correctly
    }
}
