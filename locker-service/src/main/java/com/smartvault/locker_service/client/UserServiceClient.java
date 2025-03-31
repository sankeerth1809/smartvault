package com.smartvault.locker_service.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "auth-service", url = "http://localhost:8088")  // Update the correct URL if needed
public interface UserServiceClient {

    @GetMapping("/auth/validate")  // Define API endpoint in Auth Service
    boolean validateUser(@RequestParam String debitCardNumber);
}
