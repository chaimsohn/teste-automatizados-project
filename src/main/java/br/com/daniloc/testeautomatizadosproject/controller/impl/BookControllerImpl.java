package br.com.daniloc.testeautomatizadosproject.controller.impl;

import br.com.daniloc.testeautomatizadosproject.controller.BookController;
import br.com.daniloc.testeautomatizadosproject.mapper.BookMapper;
import br.com.daniloc.testeautomatizadosproject.model.request.BookRequest;
import br.com.daniloc.testeautomatizadosproject.model.response.BookResponse;
import br.com.daniloc.testeautomatizadosproject.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/books")
public class BookControllerImpl implements BookController {

    private final BookService bookService;
    private final BookMapper bookMapper;
    @Override
    public ResponseEntity<Mono<Void>> save(final BookRequest bookRequest) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(bookService.save(bookRequest).then());
    }

    @Override
    public ResponseEntity<Mono<BookResponse>> findById(String id) {
        return ResponseEntity.ok()
                .body(bookService.findById(id).map(bookMapper :: toResponse));
    }

    @Override
    public ResponseEntity<Flux<BookResponse>> findAll() {
        return ResponseEntity.ok().body(
                bookService.findAll().map(bookMapper :: toResponse)
        );
    }

    @Override
    public ResponseEntity<Mono<BookResponse>> update(String id, BookRequest bookRequest) {
        return null;
    }

    @Override
    public ResponseEntity<Mono<Void>> delete(String id) {
        return null;
    }
}
