package com.leosbelpoll.mancala.service;

import com.leosbelpoll.mancala.model.User;
import com.leosbelpoll.mancala.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository repository;

    public User getOrCreate(String email) {
        Optional<User> optional = repository.findByEmail(email);
        if (optional.isPresent())
            return optional.get();
        User user = User.builder().email(email).build();
        return repository.save(user);
    }

}
