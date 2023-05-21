package br.com.daniloc.testeautomatizadosproject.mapper;

import br.com.daniloc.testeautomatizadosproject.entity.User;
import br.com.daniloc.testeautomatizadosproject.model.request.UserRequest;
import br.com.daniloc.testeautomatizadosproject.model.response.UserResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import static org.mapstruct.NullValueCheckStrategy.ALWAYS;
import static org.mapstruct.NullValuePropertyMappingStrategy.IGNORE;

@Mapper(
        componentModel = "spring",
        nullValuePropertyMappingStrategy = IGNORE,
        nullValueCheckStrategy = ALWAYS
)
public interface UserMapper {

    @Mapping(target = "id", ignore = true)
    User toEntity(final UserRequest userRequest);

    UserResponse toResponse(final User userEntity);
}
