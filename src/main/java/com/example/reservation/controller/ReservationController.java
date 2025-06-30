package com.example.reservation.controller;

import com.example.reservation.dto.AuthRequest;
import com.example.reservation.dto.AuthResponse;
import com.example.reservation.dto.ReservationRequest;
import com.example.reservation.dto.ReservationResponse;
import com.example.reservation.entity.Reservation;
import com.example.reservation.facade.ReservationFacade;/*
import com.example.reservation.service.ReservationService;*/
import com.example.reservation.service.AuthServiceImpl;
import com.example.reservation.service.MemberService;
import com.example.reservation.service.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@RestController
@RequestMapping("/reservation-service/api/reservations")
@CrossOrigin
public class ReservationController {

    @Autowired
    private ReservationFacade reservationFacade;

    @Autowired
    private ReservationService reservationService;

    @Autowired
    private MemberService memberService;

    @Autowired
    private AuthServiceImpl authService;

    @PostMapping
    public Mono<ResponseEntity<ReservationResponse>> createReservation( @RequestBody ReservationRequest reservationRequest) throws Exception {
        return reservationFacade.createReservation(reservationRequest);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> cancelReservation(@PathVariable Long id) {
        reservationService.cancelReservation(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/member/{memberId}")
    public Flux<ResponseEntity<Reservation>> getReservationsByMember(@PathVariable Long memberId) {
        return reservationService.getReservationsByMember(memberId);
    }

    @GetMapping("/book/{bookId}")
    public Flux<ResponseEntity<Reservation>> getReservationsByBook(@PathVariable Long bookId) {
        return reservationService.getReservationsByBook(bookId);
    }

    @GetMapping
    public Flux<ResponseEntity<Reservation>> getAllReservations() {
        return reservationService.getAllReservations();
    }
}
