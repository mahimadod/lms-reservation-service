package com.example.reservation.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Table(name="reservations")
public class Reservation {
    @Id
    private Long id;
    private Long memberId;
    private Long bookId;
    private LocalDate reservationDate;
}
