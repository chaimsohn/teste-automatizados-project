package br.com.daniloc.testeautomatizadosproject.service;

import br.com.daniloc.testeautomatizadosproject.entity.User;
import br.com.daniloc.testeautomatizadosproject.mapper.UserMapper;
import br.com.daniloc.testeautomatizadosproject.model.request.UserRequest;
import br.com.daniloc.testeautomatizadosproject.repositery.UserRepository;
import br.com.daniloc.testeautomatizadosproject.service.exception.ObjectNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import static org.hibernate.validator.internal.util.StringHelper.format;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public Mono<User> save(final UserRequest userRequest){
        return userRepository.save(userMapper.toEntity(userRequest));
    }

    public Mono<User> findById(final String id){
        return userRepository.findById(id)
                .switchIfEmpty(Mono.error(
                        new ObjectNotFoundException(
                                format("Usuário não encontrado. Id: %s, Type: %s", id, User.class.getSimpleName())
                        )
                ));
    }
}
