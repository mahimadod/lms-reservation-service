package com.example.reservation.service;

import com.example.reservation.dto.Book;
import com.example.reservation.dto.Member;
import com.example.reservation.entity.Reservation;
import org.springframework.http.ResponseEntity;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

public interface ReservationService {
 Mono<Reservation> saveReservation(Mono<Member> member, Mono<Book> book);

   void cancelReservation(Long reservationId);
    Flux<ResponseEntity<Reservation>> getReservationsByMember(Long memberId);
    Flux<ResponseEntity<Reservation>>getReservationsByBook(Long bookId);
    Flux<ResponseEntity<Reservation>> getAllReservations();

}
