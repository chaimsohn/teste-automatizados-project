package br.com.daniloc.testeautomatizadosproject.model.response;

import java.time.LocalDate;

public record BookResponse (
        String id,
        String title,
        String resume,
        String sumary,
        Double price,
        Integer nPages,
        String isbn,
        LocalDate releaseDate
) {
}
