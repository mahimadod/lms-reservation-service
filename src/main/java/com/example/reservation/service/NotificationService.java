package com.example.reservation.service;

import com.example.reservation.dto.Book;
import com.example.reservation.dto.Member;
import com.example.reservation.dto.NotificationResponse;
import reactor.core.publisher.Mono;

import java.util.Optional;

public interface NotificationService {
     Mono<NotificationResponse> sendNotification(Book book, Member member);

}
