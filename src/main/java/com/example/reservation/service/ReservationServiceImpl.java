package com.example.reservation.service;

import com.example.reservation.dto.Book;
import com.example.reservation.dto.Member;
import com.example.reservation.entity.Reservation;
import com.example.reservation.repository.ReservationReactiveRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDate;
import java.util.List;

@Service
public class ReservationServiceImpl implements ReservationService {

    @Autowired
    private ReservationReactiveRepository reservationRepository;

    @Override
    public Mono<Reservation> saveReservation(Mono<Member> member, Mono<Book> book) {
    return Mono.zip(member,book).flatMap(tuple->{
            Reservation reservation = Reservation.builder()
                    .memberId(tuple.getT1().getId())
                    .bookId(tuple.getT2().getId())
                    .reservationDate(LocalDate.now())
                    .build();
            return reservationRepository.save(reservation);
    });
    }

   @Override
    public void cancelReservation(Long reservationId) {
        reservationRepository.deleteById(reservationId);
    }

    @Override
    public Flux<ResponseEntity<Reservation>> getReservationsByMember(Long memberId) {
       return reservationRepository.findByMemberId(memberId).map(ResponseEntity::ok);
    }

    @Override
    public Flux<ResponseEntity<Reservation>> getReservationsByBook(Long bookId) {
        return reservationRepository.findByBookId(bookId).map(ResponseEntity::ok);
    }

    @Override
    public Flux<ResponseEntity<Reservation>> getAllReservations() {
        return reservationRepository.findAll().map(ResponseEntity::ok);
    }

}
