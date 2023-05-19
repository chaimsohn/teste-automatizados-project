package br.com.daniloc.testeautomatizadosproject.repositery;

import br.com.daniloc.testeautomatizadosproject.entity.Book;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
@RequiredArgsConstructor
public class BookRepository {

    private final ReactiveMongoTemplate mongoTemplate;

    public Mono<Book> save(final Book book){
        return mongoTemplate.save(book);
    }
}
