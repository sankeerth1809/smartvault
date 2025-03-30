package com.smartvault.auth_service.repository;

import com.smartvault.auth_service.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByDebitCardNumber(String debitCardNumber);
}
