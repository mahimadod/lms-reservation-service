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

    @Override
    public Mono<Book> getBookByIdMono(Long bookId) {
        WebClient bookClient = WebClient.create("lb://book-service");

        return authService.getAuthResponseMono()
                .flatMap(authResponse ->
                        bookClient.get()
                                .uri("/api/books/{bookId}", bookId)
                                .header("token", authResponse.getToken())
                                .retrieve()
                                .bodyToMono(Book.class)
                );
    }
}
