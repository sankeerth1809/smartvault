package com.ust.smart_vault.repository;

import com.ust.smart_vault.model.Card;
import com.ust.smart_vault.model.Locker;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LockerRepository extends JpaRepository<Locker,Long> {
    List<Locker> findByOwnerName(Card ownerName);
    Locker findByLockerNumberAndLockerPassword(String lockerNumber, String lockerPassword);
    Locker findByLockerNumber(String lockerNumber);
}
