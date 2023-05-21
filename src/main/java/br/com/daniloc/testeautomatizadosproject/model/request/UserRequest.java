package br.com.daniloc.testeautomatizadosproject.model.request;

import br.com.daniloc.testeautomatizadosproject.validator.TrimString;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UserRequest(
        @TrimString
        @Size(min = 3, max = 50, message = "Deve estar entre 3 e 50 caracteres")
        @NotBlank(message = "Não pode ser vazio ou nulo")
        String name,

        @TrimString
        @Email(message = "Email inválido")
        @NotBlank(message = "Não pode ser vazio ou nulo")
        String email,
        @TrimString
        @Size(min = 3, max = 50, message = "Deve estar entre 3 e 50 caracteres")
        @NotBlank(message = "Não pode ser vazio ou nulo")
        String password,
        Integer idade
) {
}
