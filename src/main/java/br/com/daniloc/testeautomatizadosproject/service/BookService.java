package br.com.daniloc.testeautomatizadosproject.service;

import br.com.daniloc.testeautomatizadosproject.entity.Book;
import br.com.daniloc.testeautomatizadosproject.mapper.BookMapper;
import br.com.daniloc.testeautomatizadosproject.model.request.BookRequest;
import br.com.daniloc.testeautomatizadosproject.repositery.BookRepository;
import br.com.daniloc.testeautomatizadosproject.service.exception.ObjectNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static org.hibernate.validator.internal.util.StringHelper.format;

@Service
@RequiredArgsConstructor
public class BookService {

    private final BookRepository bookRepository;
    private final BookMapper bookMapper;
    public Mono<Book> save(final BookRequest bookRequest){
        return bookRepository.save(bookMapper.toEntity(bookRequest));
    }

    public Mono<Book> findById(final String id){
        return bookRepository.findById(id)
                .switchIfEmpty(Mono.error(
                        new ObjectNotFoundException(
                                format("Livro não encontrado. Id: %s, Type: %s", id, Book.class.getSimpleName())
                        )
                ));
    }

    public Flux<Book> findAll(){
        return bookRepository.findAll();
    }
}

