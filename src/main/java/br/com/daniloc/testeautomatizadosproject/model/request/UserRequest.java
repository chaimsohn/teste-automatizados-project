package br.com.daniloc.testeautomatizadosproject.model.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UserRequest(
        @Size(min = 3, max = 50, message = "Deve estar entre 3 e 50 caracteres")
        @NotBlank(message = "Não pode ser vazio ou nulo")
        String name,

        @Email(message = "Email inválido")
        @NotBlank(message = "Não pode ser vazio ou nulo")
        String email,
        @Size(min = 3, max = 50, message = "Deve estar entre 3 e 50 caracteres")
        @NotBlank(message = "Não pode ser vazio ou nulo")
        String password
) {
}
