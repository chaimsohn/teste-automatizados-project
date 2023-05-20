package br.com.daniloc.testeautomatizadosproject.model.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

public record BookRequest(
        @NotBlank(message = "Não pode ser vazio ou nulo")
        String title,
        @Size(min = 20, max = 500, message = "Deve estar entre 20 e 500 caracteres")
        @NotBlank(message = "Não pode ser vazio ou nulo")
        String resume,
        String sumary,

        Double price,

        Integer nPages,
        @NotBlank(message = "Não pode ser vazio ou nulo")
        String isbn,

        LocalDate releaseDate
) {
}
