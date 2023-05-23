package br.com.daniloc.testeautomatizadosproject.controller;

import br.com.daniloc.testeautomatizadosproject.entity.Book;
import br.com.daniloc.testeautomatizadosproject.mapper.BookMapper;
import br.com.daniloc.testeautomatizadosproject.model.request.BookRequest;
import br.com.daniloc.testeautomatizadosproject.model.response.BookResponse;
import br.com.daniloc.testeautomatizadosproject.service.BookService;
import com.mongodb.reactivestreams.client.MongoClient;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDate;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.web.reactive.function.BodyInserters.fromValue;
import static reactor.core.publisher.Mono.just;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureWebTestClient
class BookControllerImplTest {

    private static final String ID = "123456";
    private static final String TITLE = "Senhor dos Aneis";
    private static final String RESUME = "Livro de token sobre a guerra mundial";
    private static final String SUMARY = "affaff123";

    private static final Double PRICE = 32.0;

    private static final String ISBN = "32";

    private static final LocalDate RELEASEDATA = LocalDate.of(2025,12,11);
    private static final String BASE_URI = "/books";

    @Autowired
    private WebTestClient webTestClient;

    @MockBean
    private BookService bookService;

    @MockBean
    private BookMapper bookMapper;

    @MockBean
    private MongoClient mongoClient;

    @Test
    @DisplayName("Test endpoint save with success")
    void testSaveWithSuccess() {
        final var bookRequest = new BookRequest(TITLE, RESUME, SUMARY, PRICE, ISBN, RELEASEDATA);

        when(bookService.save(any(BookRequest.class))).thenReturn(just(Book.builder().build()));

        webTestClient.post().uri(BASE_URI)
                .contentType(APPLICATION_JSON)
                .body(fromValue(bookRequest))
                .exchange()
                .expectStatus().isCreated();

        verify(bookService).save(any(BookRequest.class));
    }

    @Test
    @DisplayName("Test endpoint save with bad request")
    void testSaveWithBadRequest() {
        final var bookRequest = new BookRequest(TITLE.concat(" "), RESUME, SUMARY, PRICE, ISBN, RELEASEDATA);

        webTestClient.post().uri(BASE_URI)
                .contentType(APPLICATION_JSON)
                .body(fromValue(bookRequest))
                .exchange()
                .expectStatus().isBadRequest()
                .expectBody()
                .jsonPath("$.path").isEqualTo(BASE_URI)
                .jsonPath("$.status").isEqualTo(BAD_REQUEST.value())
                .jsonPath("$.error").isEqualTo("Erro de validação")
                .jsonPath("$.message").isEqualTo("Erro na validação de atributos")
                .jsonPath("$.errors[0].fieldName").isEqualTo("title")
                .jsonPath("$.errors[0].message").isEqualTo("Não pode conter espaços no ínício e final");

    }

    @Test
    @DisplayName("Test find by id endpoint with success")
    void testFindByIdWithSuccess() {
        final var bookResponse = new BookResponse(ID, TITLE, RESUME, SUMARY, PRICE, ISBN, RELEASEDATA);

        when(bookService.findById(anyString())).thenReturn(Mono.just(Book.builder().build()));
        when(bookMapper.toResponse(any(Book.class))).thenReturn(bookResponse);

        webTestClient.get().uri(BASE_URI + "/" + ID)
                .accept(APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.id").isEqualTo(ID)
                .jsonPath("$.title").isEqualTo(TITLE)
                .jsonPath("$.resume").isEqualTo(RESUME)
                .jsonPath("$.sumary").isEqualTo(SUMARY)
                .jsonPath("$.price").isEqualTo(PRICE)
                .jsonPath("$.isbn").isEqualTo(ISBN)
                .jsonPath("$.releaseDate").isEqualTo(RELEASEDATA.toString());

        verify(bookService).findById(anyString());
        verify(bookMapper).toResponse(any(Book.class));
    }

    @Test
    @DisplayName("Test find all endpoint with success")
    void testFindAllWithSuccess() {
        final var bookResponse = new BookResponse(ID, TITLE, RESUME, SUMARY, PRICE, ISBN, RELEASEDATA);

        when(bookService.findAll()).thenReturn(Flux.just(Book.builder().build()));
        when(bookMapper.toResponse(any(Book.class))).thenReturn(bookResponse);

        webTestClient.get().uri(BASE_URI)
                .accept(APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.[0].id").isEqualTo(ID)
                .jsonPath("$.[0].title").isEqualTo(TITLE)
                .jsonPath("$.[0].resume").isEqualTo(RESUME)
                .jsonPath("$.[0].sumary").isEqualTo(SUMARY)
                .jsonPath("$.[0].price").isEqualTo(PRICE)
                .jsonPath("$.[0].isbn").isEqualTo(ISBN)
                .jsonPath("$.[0].releaseDate").isEqualTo(RELEASEDATA.toString());

        verify(bookService).findAll();
        verify(bookMapper).toResponse(any(Book.class));
    }

    @Test
    @DisplayName("Test update endpoint with success")
    void testUpdateWithSuccess() {
        final var bookRequest = new BookRequest(TITLE, RESUME, SUMARY, PRICE, ISBN, RELEASEDATA);
        final var bookResponse = new BookResponse(ID, TITLE, RESUME, SUMARY, PRICE, ISBN, RELEASEDATA);

        when(bookService.update(anyString(), any(BookRequest.class)))
                .thenReturn(just(Book.builder().build()));
        when(bookMapper.toResponse(any(Book.class))).thenReturn(bookResponse);

        webTestClient.patch().uri(BASE_URI + "/" + ID)
                .contentType(APPLICATION_JSON)
                .body(fromValue(bookRequest))
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.id").isEqualTo(ID)
                .jsonPath("$.title").isEqualTo(TITLE)
                .jsonPath("$.resume").isEqualTo(RESUME)
                .jsonPath("$.sumary").isEqualTo(SUMARY)
                .jsonPath("$.price").isEqualTo(PRICE)
                .jsonPath("$.isbn").isEqualTo(ISBN)
                .jsonPath("$.releaseDate").isEqualTo(RELEASEDATA.toString());

        verify(bookService).update(anyString(), any(BookRequest.class));
        verify(bookMapper).toResponse(any(Book.class));

    }

    @Test
    @DisplayName("Test delete endpoint with success")
    void testDeleteWithSuccess() {
        when(bookService.delete(anyString())).thenReturn(just(Book.builder().build()));

        webTestClient.delete().uri(BASE_URI + "/" + ID)
                .exchange()
                .expectStatus().isOk();

        verify(bookService).delete(anyString());
    }
}