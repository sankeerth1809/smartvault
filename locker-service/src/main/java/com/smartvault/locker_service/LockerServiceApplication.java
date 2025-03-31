package com.smartvault.locker_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class  LockerServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(LockerServiceApplication.class, args);
	}

}
