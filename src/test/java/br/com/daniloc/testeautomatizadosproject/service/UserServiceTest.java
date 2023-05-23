package br.com.daniloc.testeautomatizadosproject.service;

import br.com.daniloc.testeautomatizadosproject.entity.User;
import br.com.daniloc.testeautomatizadosproject.mapper.UserMapper;
import br.com.daniloc.testeautomatizadosproject.model.request.UserRequest;
import br.com.daniloc.testeautomatizadosproject.repositery.UserRepository;
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

import static java.lang.String.format;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserMapper userMapper;

    @InjectMocks
    private UserService userService;

    @Test
    void testSave() {
        UserRequest request = new UserRequest("Danilo", "danilo@mail.com", "affaff123", 32);
        User entity = User.builder().build();

        when(userMapper.toEntity(any(UserRequest.class))).thenReturn(entity);
        when(userRepository.save(any(User.class))).thenReturn(Mono.just(User.builder().build()));

        Mono<User> result = userService.save(request);

        StepVerifier.create(result)
                .expectNextMatches(user -> user.getClass() == User.class)
                .expectComplete()
                .verify();

        Mockito.verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    void testFindById() {
        when(userRepository.findById(anyString())).thenReturn(Mono.just(User.builder().build()));

        Mono<User> result = userService.findById("123");

        StepVerifier.create(result)
                .expectNextMatches(user -> user.getClass() == User.class)
                .expectComplete()
                .verify();

        Mockito.verify(userRepository, times(1)).findById(anyString());
    }

    @Test
    void testFindAll() {
        when(userRepository.findAll()).thenReturn(Flux.just(User.builder().build()));

        Flux<User> result = userService.findAll();

        StepVerifier.create(result)
                .expectNextMatches(user -> user.getClass() == User.class)
                .expectComplete()
                .verify();

        Mockito.verify(userRepository, times(1)).findAll();
    }

    @Test
    void testUpdate() {
        UserRequest request = new UserRequest("Danilo", "danilo@mail.com", "affaff123", 32);
        User entity = User.builder().build();

        when(userMapper.toEntity(any(UserRequest.class), any(User.class))).thenReturn(entity);
        when(userRepository.findById(anyString())).thenReturn(Mono.just(entity));
        when(userRepository.save(any(User.class))).thenReturn(Mono.just(entity));

        Mono<User> result = userService.update("123", request);

        StepVerifier.create(result)
                .expectNextMatches(user -> user.getClass() == User.class)
                .expectComplete()
                .verify();

        Mockito.verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    void testDelete() {
        User entity = User.builder().build();
        when(userRepository.findAndRemove(anyString())).thenReturn(Mono.just(entity));

        Mono<User> result = userService.delete("123");

        StepVerifier.create(result)
                .expectNextMatches(user -> user.getClass() == User.class)
                .expectComplete()
                .verify();

        Mockito.verify(userRepository, times(1)).findAndRemove(anyString());
    }

    @Test
    void testHandleNotFound() {
        when(userRepository.findById(anyString())).thenReturn(Mono.empty());
        try {
            userService.findById("123").block();
        } catch (Exception ex) {
            assertEquals(ObjectNotFoundException.class, ex.getClass());
            assertEquals(format("Object not found. Id: %s, Type: %s", "123", User.class.getSimpleName()), ex.getMessage());
        }
    }


}