package br.com.daniloc.testeautomatizadosproject.service;

import br.com.daniloc.testeautomatizadosproject.entity.Book;
import br.com.daniloc.testeautomatizadosproject.entity.User;
import br.com.daniloc.testeautomatizadosproject.mapper.BookMapper;
import br.com.daniloc.testeautomatizadosproject.model.request.BookRequest;
import br.com.daniloc.testeautomatizadosproject.repositery.BookRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;

import static org.mockito.Mockito.*;
@ExtendWith(MockitoExtension.class)
class BookServiceTest {
    @Mock
    private BookRepository repository;
    @Mock
    private BookMapper mapper;
    @InjectMocks
    private BookService service;

    @Test
    void testSave() {
        BookRequest request = new BookRequest("Harry Potter", "Text", "1", "23,05", "56", "25/02/2000");
        Book entity = Book.builder().build();

        when(mapper.toEntity(any(BookRequest.class))).thenReturn(entity);
        when(repository.save(any(Book.class))).thenReturn(Mono.just(Book.builder().build()));

        Mono<Book> result = service.save(request);

        StepVerifier.create(result)
                .expectNextMatches(book -> book.getClass() == Book.class)
                .expectComplete()
                .verify();

        Mockito.verify(repository, times(1)).save(any(Book.class));
    }

    @Test
    void testFindById() {
        when(repository.findById(anyString())).thenReturn(Mono.just(Book.builder().build()));

        Mono<Book> result = service.findById("123");

        StepVerifier.create(result)
                .expectNextMatches(book -> book.getClass() == Book.class)
                .expectComplete()
                .verify();

        Mockito.verify(repository, times(1)).findById(anyString());
    }

    @Test
    void testFindAll() {
        when(repository.findAll()).thenReturn(Flux.just(Book.builder().build()));

        Flux<Book> result = service.findAll();

        StepVerifier.create(result)
                .expectNextMatches(book -> book.getClass() == Book.class)
                .expectComplete()
                .verify();

        Mockito.verify(repository, times(1)).findAll();
    }

    @Test
    void testUpdate() {

        BookRequest request = new BookRequest("Harry Potter", "Text", "1", "23,05", "56", "25/02/2000");
        Book entity = Book.builder().build();

        when(mapper.toEntity(any(BookRequest.class), any(Book.class))).thenReturn(entity);
        when(repository.findById(anyString())).thenReturn(Mono.just(entity));
        when(repository.save(any(Book.class))).thenReturn(Mono.just(entity));

        Mono<Book> result = service.update("123", request);

        StepVerifier.create(result)
                .expectNextMatches(book -> book.getClass() == Book.class)
                .expectComplete()
                .verify();

        Mockito.verify(repository, times(1)).save(any(Book.class));
    }

}
