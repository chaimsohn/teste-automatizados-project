package br.com.daniloc.testeautomatizadosproject.service;

import br.com.daniloc.testeautomatizadosproject.entity.User;
import br.com.daniloc.testeautomatizadosproject.mapper.UserMapper;
import br.com.daniloc.testeautomatizadosproject.model.request.UserRequest;
import br.com.daniloc.testeautomatizadosproject.repositery.UserRepository;
import br.com.daniloc.testeautomatizadosproject.service.exception.ObjectNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
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
        return errorNotFound(userRepository.findById(id), id);
    }

    public Flux<User> findAll(){
        return userRepository.findAll();
    }

    public Mono<User> update(final String id, final UserRequest userRequest){
        return findById(id)
                .map(entity -> userMapper.toEntity(userRequest, entity))
                .flatMap(userRepository::save);
    }

    public Mono<User> delete(final String id){
        return errorNotFound(userRepository.findAndRemove(id), id);
    }

    private <T> Mono<T> errorNotFound(Mono<T> mono, String id){
        return mono.switchIfEmpty(Mono.error(
                new ObjectNotFoundException(
                        format("Usuário não encontrado. Id: %s, Type: %s", id, User.class.getSimpleName())
                )
        ));
    }
}
