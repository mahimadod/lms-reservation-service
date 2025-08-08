package com.example.reservation.facade;

import com.example.reservation.dto.*;
import com.example.reservation.service.BookService;
import com.example.reservation.service.MemberService;/*
import com.example.reservation.service.NotificationService;*//*
import com.example.reservation.service.ReservationService;*/
import com.example.reservation.service.NotificationService;
import com.example.reservation.service.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

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
            Mono<Member> member=memberService.getMemberByIdMono(reservationRequest.getMemberId());
            Mono<Book> book=bookService.getBookByIdMono(reservationRequest.getBookId());
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
