package com.example.reservation;

import jakarta.annotation.PostConstruct;
import org.slf4j.MDC;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.data.r2dbc.repository.config.EnableR2dbcRepositories;

@EnableR2dbcRepositories(basePackages = "com.example.reservation.repository")
@SpringBootApplication
@EnableCaching
@EnableDiscoveryClient
public class ReservationServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(ReservationServiceApplication.class, args);
	}
	@PostConstruct
	public void initMDC() {
		MDC.put("service", "authenticator-service");
	}
}
