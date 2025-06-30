package com.example.reservation.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Book {
    private Long id;
    private String title;
    private String isbn;
    private Author author;
    private Publisher publisher;
    private Category category;
    private int totalCopies;
    private int availableCopies;

}
