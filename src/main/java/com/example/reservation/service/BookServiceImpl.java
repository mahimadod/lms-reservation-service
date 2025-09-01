package com.example.reservation.service;

import com.example.reservation.dto.AuthRequest;
import com.example.reservation.dto.AuthResponse;
import com.example.reservation.dto.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class BookServiceImpl implements BookService {

    @Autowired
    AuthServiceImpl authService;

    private final WebClient.Builder webClientBuilder;

    public BookServiceImpl(WebClient.Builder webClientBuilder) {
        this.webClientBuilder = webClientBuilder;
    }

    @Override
    public Mono<Book> getBookByIdMono(Long bookId) {
        return authService.getAuthResponseMono()
                .flatMap(authResponse ->
                        webClientBuilder.baseUrl("lb://spring-cloud-gateway-service/").build().get()
                                .uri("book-service/api/books/{bookId}", bookId)
                                .header("token", authResponse.getToken())
                                .retrieve()
                                .bodyToMono(Book.class)
                );
    }
}
