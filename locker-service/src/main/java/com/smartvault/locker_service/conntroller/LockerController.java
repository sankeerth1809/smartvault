package com.smartvault.locker_service.conntroller;

import com.smartvault.locker_service.model.Locker;
import com.smartvault.locker_service.service.LockerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/lockers")
public class LockerController {

    @Autowired
    private LockerService lockerService;

    // ✅ Register a new locker for a user
    @PostMapping("/register")
    public Map<String, String> registerLocker(
            @RequestParam String debitCardNumber,
            @RequestParam String lockerNumber,
            @RequestParam double hourlyRate,
            @RequestParam String lockerPassword) {

        String message = lockerService.registerLocker(debitCardNumber, lockerNumber, hourlyRate, lockerPassword);
        Map<String, String> response = new HashMap<>();
        response.put("message", message);
        return response;
    }

    @PostMapping("/user/access-locker")
    public ResponseEntity<Map<String, String>> accessUserLocker(
            @RequestParam String debitCardNumber,
            @RequestParam String lockerNumber,
            @RequestParam String lockerPassword) {

        boolean accessGranted = lockerService.verifyLockerAccess(debitCardNumber, lockerNumber, lockerPassword);
        Map<String, String> response = new HashMap<>();

        if (accessGranted) {
            response.put("message", "Access granted to locker: " + lockerNumber);
            return ResponseEntity.ok(response);
        } else {
            response.put("error", "Incorrect password! Access denied.");
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(response);
        }
    }

    @PostMapping("/user/lock")
    public ResponseEntity<Map<String, String>> lockUserLocker(
            @RequestParam String debitCardNumber,
            @RequestParam String lockerNumber) {

        boolean locked = lockerService.lockLocker(debitCardNumber, lockerNumber);
        Map<String, String> response = new HashMap<>();

        if (locked) {
            response.put("message", "Locker " + lockerNumber + " successfully locked.");
            return ResponseEntity.ok(response);
        } else {
            response.put("error", "Failed to lock the locker. Please try again.");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }


    // ✅ Release a locker and calculate the bill
    @PostMapping("/release")
    public Map<String, Object> releaseLocker(
            @RequestParam String debitCardNumber,
            @RequestParam String lockerNumber,
            @RequestParam String lockerPassword) {

        double totalCost = lockerService.releaseLocker(debitCardNumber, lockerNumber, lockerPassword);
        Map<String, Object> response = new HashMap<>();
        response.put("lockerNumber", lockerNumber);
        response.put("totalCost", totalCost);
        return response;
    }

    // ✅ Get all lockers assigned to a user
    @GetMapping("/user-lockers")
    public List<Locker> getUserLockers(@RequestParam String debitCardNumber) {
        return lockerService.getLockersByUser(debitCardNumber);
    }
}