package br.com.daniloc.testeautomatizadosproject.model.request;

public record UserRequest(
        String name,
        String email,
        String password
) {
}
