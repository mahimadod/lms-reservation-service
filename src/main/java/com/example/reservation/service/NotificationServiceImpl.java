package com.example.reservation.service;

import com.example.reservation.dto.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
@Slf4j
@Service
public class NotificationServiceImpl implements NotificationService {
    @Autowired
    AuthServiceImpl authService;

    @Override
    public Mono<NotificationResponse> sendNotification(Book book, Member member) {
        WebClient notificationClient = WebClient.create("lb://notification-service/api/notify");
        NotificationRequest notification = NotificationRequest.builder()
                .email(member.getEmail())
                .title(book.getTitle())
                .author(book.getAuthor())
                .name(member.getName())
                .build();

 return authService.getAuthResponseMono().flatMap(authResponse -> notificationClient
                         .post()
                         .header("token", authResponse.getToken())
                         .body(BodyInserters.fromValue(notification))
                         .exchangeToMono(response -> {
                             if (response.statusCode().equals(HttpStatus.OK)) {
                                 return response.bodyToMono(NotificationResponse.class);
                             } else {
                                 log.info("Notification service failed with status: {}", response.statusCode());
                                 return Mono.just(NotificationResponse.builder().message("FAILURE").build());
                             }
                         }));
    }
}
