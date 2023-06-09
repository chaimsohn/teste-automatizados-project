package br.com.daniloc.testeautomatizadosproject.controller;

import br.com.daniloc.testeautomatizadosproject.model.request.BookRequest;
import br.com.daniloc.testeautomatizadosproject.model.response.BookResponse;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface BookController {

    @PostMapping
    ResponseEntity<Mono<Void>> save(@Valid @RequestBody BookRequest bookRequest);

    @GetMapping(value = "/{id}")
    ResponseEntity<Mono<BookResponse>> findById(@PathVariable String id);

    @GetMapping
    ResponseEntity<Flux<BookResponse>> findAll();

    @PatchMapping(value = "/{id}")
    ResponseEntity<Mono<BookResponse>> update(@PathVariable String id, @RequestBody BookRequest bookRequest);

    @DeleteMapping(value = "/{id}")
    ResponseEntity<Mono<Void>> delete(@PathVariable String id);
}

