package br.com.daniloc.testeautomatizadosproject.model.request;

import java.time.LocalDate;

public record BookRequest(
        String title,
        String resume,
        String sumary,
        Double price,
        String isbn,
        LocalDate releaseDate
) {
}
