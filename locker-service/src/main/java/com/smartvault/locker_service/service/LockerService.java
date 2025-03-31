package com.smartvault.locker_service.service;

import com.smartvault.locker_service.model.Locker;
import com.smartvault.locker_service.repository.LockerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class LockerService {

    @Autowired
    private LockerRepository lockerRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    // Assign Locker
    public String registerLocker(String debitCardNumber, String lockerNumber, double hourlyRate, String pass) {
        Locker locker = new Locker();
        locker.setDebitCardNumber(debitCardNumber);
        locker.setLockerNumber(lockerNumber);
        locker.setHourlyRate(hourlyRate);
        locker.setStartTime(LocalDateTime.now());
        locker.setLockerPassword(pass, passwordEncoder);

        lockerRepository.save(locker);
        return "Locker " + lockerNumber + " assigned successfully.";
    }

    public boolean verifyLockerAccess(String debitCardNumber, String lockerNumber, String enteredPassword) {
        Optional<Locker> lockerOptional = lockerRepository.findByLockerNumberAndDebitCardNumber(lockerNumber, debitCardNumber);

        if (lockerOptional.isEmpty()) {
            return false;  // Locker not found
        }

        Locker locker = lockerOptional.get();
        locker.setLocked(false);
        lockerRepository.save(locker);
        System.out.println(locker.getLockerPassword());
        return locker.checkPin(enteredPassword, passwordEncoder); // Verify password
    }

    public boolean lockLocker(String debitCardNumber, String lockerNumber) {
        Optional<Locker> lockerOptional = lockerRepository.findByLockerNumberAndDebitCardNumber(lockerNumber, debitCardNumber);

        if (lockerOptional.isEmpty()) {
            return false; // Locker not found
        }

        Locker locker = lockerOptional.get();
        locker.setLocked(true);  // Set the locker status to "locked"
        lockerRepository.save(locker);
        return true;
    }


    // Release Locker & Calculate Bill
    public double releaseLocker(String debitCardNumber, String lockerNumber, String lockerPassword) {
        Optional<Locker> lockerOptional = lockerRepository.findByLockerNumberAndDebitCardNumber(lockerNumber, debitCardNumber);
        double totalCost = 0.0;
        if (lockerOptional.isEmpty()) {
            throw new RuntimeException("Locker not found or unauthorized access.");
        }

        Locker locker = lockerOptional.get();

        if(!locker.checkPin(lockerPassword, passwordEncoder)){
            throw new RuntimeException("Unauthorized Access");
        }
        else{
            locker.setEndTime(LocalDateTime.now());

            // Calculate cost
            Duration duration = Duration.between(locker.getStartTime(), locker.getEndTime());
            long hours = duration.toMinutes();
            totalCost = hours * locker.getHourlyRate();
            locker.setTotalCost(totalCost);

            lockerRepository.deleteById(locker.getId());
        }

        return totalCost;
    }

    // Fetch All Lockers for a User
    public List<Locker> getLockersByUser(String debitCardNumber) {
        return lockerRepository.findByDebitCardNumber(debitCardNumber);
    }
}
