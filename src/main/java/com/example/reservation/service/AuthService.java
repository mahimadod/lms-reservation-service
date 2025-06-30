package com.example.reservation.service;

import com.example.reservation.dto.AuthRequest;
import com.example.reservation.dto.AuthResponse;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

public interface AuthService {
    Mono<AuthResponse> getAuthResponseMono() ;
}
