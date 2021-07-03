package com.leosbelpoll.mancala.service;

import com.leosbelpoll.mancala.model.User;
import com.leosbelpoll.mancala.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class UserServiceTest {

    @Mock
    private UserRepository repository;

    private UserService service;

    @BeforeEach
    void setup() {
        service = new UserService(repository);
    }

    @Test
    void whenFindExistUser_ThenGetUser() {
        User mockUser = User.builder()
                .id(1L)
                .email("user1@gmail.com")
                .build();
        Mockito.when(repository.findByEmail(Mockito.anyString())).thenReturn(Optional.of(mockUser));

        User user = service.getOrCreate("user1@gmail.com");
        assertEquals(mockUser, user);
    }

    @Test
    void whenFindNotExistUser_ThenCreateAndGetUser() {
        User mockUser = User.builder()
                .id(10L)
                .email("user1@gmail.com")
                .build();
        Mockito.when(repository.findByEmail(Mockito.anyString())).thenReturn(Optional.empty());
        Mockito.when(repository.save(Mockito.any(User.class))).thenReturn(mockUser);

        User user = service.getOrCreate("user1@gmail.com");
        assertEquals(mockUser, user);
    }
}