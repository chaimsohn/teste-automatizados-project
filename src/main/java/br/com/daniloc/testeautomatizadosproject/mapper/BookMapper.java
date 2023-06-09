package br.com.daniloc.testeautomatizadosproject.mapper;

import br.com.daniloc.testeautomatizadosproject.entity.Book;
import br.com.daniloc.testeautomatizadosproject.model.request.BookRequest;
import br.com.daniloc.testeautomatizadosproject.model.response.BookResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import static org.mapstruct.NullValueCheckStrategy.ALWAYS;
import static org.mapstruct.NullValuePropertyMappingStrategy.IGNORE;

@Mapper(
        componentModel = "spring",
        nullValuePropertyMappingStrategy = IGNORE,
        nullValueCheckStrategy = ALWAYS
)
public interface BookMapper {

    @Mapping(target = "id", ignore = true)
    Book toEntity(final BookRequest bookRequest);

    @Mapping(target = "id", ignore = true)
    Book toEntity(final BookRequest bookRequest, @MappingTarget final Book entity);

    BookResponse toResponse(final Book bookEntity);
}
