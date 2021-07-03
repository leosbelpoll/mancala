package com.leosbelpoll.mancala.repository;

import com.leosbelpoll.mancala.model.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    void whenFindUserByEmail_returnUser(){
        Optional<User> optional = userRepository.findByEmail("user1@gmail.com");
        assertTrue(optional.isPresent());
    }

    @Test
    void whenFindUserByEmail_returnNull(){
        Optional<User> optional = userRepository.findByEmail("mock@gmail.com");
        assertFalse(optional.isPresent());
    }

    @Test
    void whenCreateUser_thenOk(){
        String email = "new_user@gmail.com";
        User user = User.builder().email(email).build();
        assertDoesNotThrow(() -> userRepository.save(user));
        assertEquals(email, user.getEmail());
        assertTrue(user.getId() > 1);
    }
}