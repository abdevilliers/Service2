package com.christy.Service2.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class BookDto {
    private Integer bookId;
    private Double price;
    private String author;
    private String title;
}
