package com.example.reservation.facade;

import com.example.reservation.dto.*;
import com.example.reservation.service.BookService;
import com.example.reservation.service.MemberService;/*
import com.example.reservation.service.NotificationService;*//*
import com.example.reservation.service.ReservationService;*/
import com.example.reservation.service.NotificationService;
import com.example.reservation.service.ReservationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Slf4j
@Service
public class ReservationFacade {

    @Autowired
    private BookService bookService;

    @Autowired
    private MemberService memberService;

    @Autowired
    private ReservationService reservationService;

    @Autowired
    private NotificationService notificationService;

   public Mono<ResponseEntity<ReservationResponse>> createReservation(ReservationRequest reservationRequest) throws Exception {
       log.info("GOIN INSIDE MEMBER SERVICE");
       Mono<Member> member=memberService.getMemberByIdMono(reservationRequest.getMemberId());
       log.info("GOIN INSIDE BOOK SERVICE");
       Mono<Book> book=bookService.getBookByIdMono(reservationRequest.getBookId());
       log.info("COMING OUT MEMBER SERVICE");
       return Mono.zip(member, book)
                   .flatMap(tuple -> {
                       Member memberResp = tuple.getT1();
                       Book bookResp = tuple.getT2();

                       return reservationService.saveReservation(Mono.just(memberResp), Mono.just(bookResp))
                               .flatMap(res ->
                                       notificationService.sendNotification(bookResp, memberResp)
                                               .map(notification -> ResponseEntity.ok(
                                                       ReservationResponse.builder()
                                                               .message(notification.getMessage())
                                                               .build()
                                               )));
                   });

    }
}
