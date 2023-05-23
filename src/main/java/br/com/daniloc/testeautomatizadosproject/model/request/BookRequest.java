package br.com.daniloc.testeautomatizadosproject.model.request;

import br.com.daniloc.testeautomatizadosproject.validator.Date;
import br.com.daniloc.testeautomatizadosproject.validator.MinPrice;
import br.com.daniloc.testeautomatizadosproject.validator.TrimString;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

public record BookRequest(
        @TrimString
        @NotBlank(message = "Não pode ser vazio ou nulo")
        String title,
        @Size(min = 20, max = 500, message = "Deve estar entre 20 e 500 caracteres")
        @NotBlank(message = "Não pode ser vazio ou nulo")
        String resume,

        @NotBlank
        String sumary,

        @MinPrice
        Double price,

        @TrimString
        @NotBlank(message = "Não pode ser vazio ou nulo")
        String isbn,

        @NotNull
        @Date
        LocalDate releaseDate
) {
}
