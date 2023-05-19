package br.com.daniloc.testeautomatizadosproject.entity;


import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;

@Data
@Builder
@Document
public class Book {

    @Id
    private String id;
    private String title;
    private String resume;
    private String sumary;
    private Double price;
    @Indexed(unique = true)
    private String isbn;
    private LocalDate releaseDate;
}
