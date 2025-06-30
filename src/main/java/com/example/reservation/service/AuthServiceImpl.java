package com.example.reservation.service;

import com.example.reservation.dto.AuthRequest;
import com.example.reservation.dto.AuthResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.time.Duration;

@Service
public class AuthServiceImpl implements AuthService {

    @Autowired
    private ReactiveRedisTemplate<String, AuthResponse> redisTemplate;


    public Mono<AuthResponse> getAuthResponseMono() {
        String cacheKey = "auth:token";

        return redisTemplate.opsForValue().get(cacheKey)
                .doOnNext(val -> System.out.println("✅ CACHE HIT: " + val))
                .switchIfEmpty(
                        WebClient.create("http://localhost:8085/auth-service")
                                .post()
                                .uri("/login")
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
