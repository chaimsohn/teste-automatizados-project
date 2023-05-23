package br.com.daniloc.testeautomatizadosproject.service;

import br.com.daniloc.testeautomatizadosproject.entity.Book;
import br.com.daniloc.testeautomatizadosproject.mapper.BookMapper;
import br.com.daniloc.testeautomatizadosproject.model.request.BookRequest;
import br.com.daniloc.testeautomatizadosproject.repositery.BookRepository;
import br.com.daniloc.testeautomatizadosproject.service.exception.ObjectNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.time.LocalDate;

import static java.lang.String.format;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BookServiceTest {

    @Mock
    private BookRepository bookRepository;

    @Mock
    private BookMapper bookMapper;

    @InjectMocks
    private BookService bookService;

    @Test
    void save() {
        final LocalDate releaseDate = LocalDate.of(2025, 12, 11);
        BookRequest bookRequest = new BookRequest("Danilo", "danilo@gmail.com", "12345", 31.5, "25", releaseDate);
        Book entity = Book.builder().build();

        when(bookMapper.toEntity(any(BookRequest.class))).thenReturn(entity);
        when(bookRepository.save(any(Book.class))).thenReturn(Mono.just(Book.builder().build()));

        Mono<Book> result = bookService.save(bookRequest);

        StepVerifier.create(result)
                .expectNextMatches(book -> book.getClass() == Book.class)
                .expectComplete()
                .verify();

        Mockito.verify(bookRepository, times(1)).save(any(Book.class));
    }

    @Test
    void testFindById() {
        when(bookRepository.findById(anyString())).thenReturn(Mono.just(Book.builder().build()));

        Mono<Book> result = bookService.findById("123");

        StepVerifier.create(result)
                .expectNextMatches(book -> book.getClass() == Book.class)
                .expectComplete()
                .verify();

        Mockito.verify(bookRepository, times(1)).findById(anyString());
    }

    @Test
    void testFindAll() {
        when(bookRepository.findAll()).thenReturn(Flux.just(Book.builder().build()));

        Flux<Book> result = bookService.findAll();

        StepVerifier.create(result)
                .expectNextMatches(book -> book.getClass() == Book.class)
                .expectComplete()
                .verify();

        Mockito.verify(bookRepository, times(1)).findAll();
    }

    @Test
    void testUpdate() {
        final LocalDate releaseDate = LocalDate.of(2025, 12, 11);
        BookRequest bookRequest = new BookRequest("Danilo", "danilo@gmail.com", "12345", 31.5, "25", releaseDate);
        Book entity = Book.builder().build();

        when(bookMapper.toEntity(any(BookRequest.class), any(Book.class))).thenReturn(entity);
        when(bookRepository.findById(anyString())).thenReturn(Mono.just(entity));
        when(bookRepository.save(any(Book.class))).thenReturn(Mono.just(entity));

        Mono<Book> result = bookService.update("123", bookRequest);

        StepVerifier.create(result)
                .expectNextMatches(book -> book.getClass() == Book.class)
                .expectComplete()
                .verify();

        Mockito.verify(bookRepository, times(1)).save(any(Book.class));
    }

    @Test
    void testDelete() {
        Book entity = Book.builder().build();
        when(bookRepository.findAndRemove(anyString())).thenReturn(Mono.just(entity));

        Mono<Book> result = bookService.delete("123");

        StepVerifier.create(result)
                .expectNextMatches(book -> book.getClass() == Book.class)
                .expectComplete()
                .verify();

        Mockito.verify(bookRepository, times(1)).findAndRemove(anyString());
    }

    @Test
    void testHandleNotFound() {
        when(bookRepository.findById(anyString())).thenReturn(Mono.empty());
        try {
            bookService.findById("123").block();
        } catch (Exception ex) {
            assertEquals(ObjectNotFoundException.class, ex.getClass());
            assertEquals(format("Livro não encontrado. Id: %s, Type: %s", "123", Book.class.getSimpleName()), ex.getMessage());
        }
    }
}