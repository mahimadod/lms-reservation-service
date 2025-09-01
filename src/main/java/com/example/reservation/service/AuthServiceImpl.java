package com.example.reservation.service;

import com.example.reservation.dto.AuthRequest;
import com.example.reservation.dto.AuthResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.time.Duration;
@Slf4j
@Service
public class AuthServiceImpl implements AuthService {

    @Autowired
    private ReactiveRedisTemplate<String, AuthResponse> redisTemplate;

    private final WebClient.Builder webClientBuilder;

    public AuthServiceImpl(WebClient.Builder webClientBuilder) {
        this.webClientBuilder = webClientBuilder;
    }

    public Mono<AuthResponse> getAuthResponseMono() {
        String cacheKey = "auth:token";
        log.info("***********2***********");
        return redisTemplate.opsForValue().get(cacheKey)
                .doOnNext(val -> System.out.println("✅ CACHE HIT: " + val))
                .switchIfEmpty(
                        webClientBuilder.build()
                                .post()
                                .uri("lb://spring-cloud-gateway-service/authenticator-service/auth-service/login")
                                .bodyValue(AuthRequest.builder().username("admin").password("adminpass").build())
                                .retrieve()
                                .bodyToMono(AuthResponse.class)
                                .flatMap(auth -> redisTemplate.opsForValue()
                                        .set(cacheKey, auth, Duration.ofMinutes(10))
                                        .thenReturn(auth)
                                )
                );
    }

}
