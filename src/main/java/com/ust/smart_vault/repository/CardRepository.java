package com.ust.smart_vault.repository;

import com.ust.smart_vault.model.Card;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CardRepository extends JpaRepository<Card, Long> {
    Card findByCardNumber(String cardNumber);
    Card findByOwnerName(String ownerName);
}
