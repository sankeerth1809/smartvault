package com.smartvault.locker_service.repository;

import com.smartvault.locker_service.model.Locker;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface LockerRepository extends JpaRepository<Locker, Long> {
    List<Locker> findByDebitCardNumber(String debitCardNumber);
    Optional<Locker> findByLockerNumberAndDebitCardNumber(String lockerNumber, String debitCardNumber);
}