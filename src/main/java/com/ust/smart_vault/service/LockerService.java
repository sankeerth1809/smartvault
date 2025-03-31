package com.ust.smart_vault.service;

import com.ust.smart_vault.model.Card;
import com.ust.smart_vault.model.Locker;
import com.ust.smart_vault.repository.CardRepository;
import com.ust.smart_vault.repository.LockerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LockerService {
    @Autowired
    private LockerRepository lockerRepository;

    @Autowired
    private CardRepository cardRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public String createLocker(Locker locker, Card owner) {
        locker.setOwner(owner); // Set the owner as the Card entity
        locker.setLockerPassword(passwordEncoder.encode(locker.getLockerPassword())); // Hash the password
        lockerRepository.save(locker); // Save the locker to the database
        return "Locker created successfully!";
    }

//    public List<Locker> getLockersByOwner(String ownerName) {
//        // Find the Card entity associated with the owner's username or card number
//        Locker owner = ownerName.findByOwnerName(ownerName);
//        if (owner == null) {
//            throw new RuntimeException("Owner not found");
//        }
//        // Retrieve lockers associated with this Card (owner)
//        return lockerRepository.findByOwnerName(owner.get);
//    }


    public boolean accessLocker(String lockerNumber, String lockerPassword) {
        Locker locker = lockerRepository.findByLockerNumber(lockerNumber);
        return locker != null && passwordEncoder.matches(lockerPassword, locker.getLockerPassword()); // Password matched, access granted
// Invalid password or locker does not exist
    }


}
