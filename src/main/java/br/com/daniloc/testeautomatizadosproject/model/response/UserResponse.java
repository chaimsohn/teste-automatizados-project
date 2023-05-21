package br.com.daniloc.testeautomatizadosproject.model.response;

public record UserResponse (
        String id,
        String name,
        String email,
        String password,
        Integer idade
) {
}
