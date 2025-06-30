package com.example.reservation.service;

import com.example.reservation.dto.AuthResponse;
import com.example.reservation.dto.Book;
import reactor.core.publisher.Mono;

import java.util.Optional;

public interface BookService {
/*     Optional<Book> getBookById(Long id);*/
     Mono<Book> getBookByIdMono(Long memberId);

}
