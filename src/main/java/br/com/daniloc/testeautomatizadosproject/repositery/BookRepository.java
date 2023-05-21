package br.com.daniloc.testeautomatizadosproject.repositery;

import br.com.daniloc.testeautomatizadosproject.entity.Book;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
@RequiredArgsConstructor
public class BookRepository {

    private final ReactiveMongoTemplate mongoTemplate;

    public Mono<Book> save(final Book book){
        return mongoTemplate.save(book);
    }

    public Mono<Book> findById(String id) {
        return mongoTemplate.findById(id, Book.class);
    }

    public Flux<Book> findAll() {
        return mongoTemplate.findAll(Book.class);
    }

    public Mono<Book> findAndRemove(String id) {
        Query query = new Query();
        Criteria where = Criteria.where("id").is(id);
        return mongoTemplate.findAndRemove(query.addCriteria(where), Book.class);
    }
}
