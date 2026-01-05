package com.recetasAPI.serviceImpl;

import com.recetasAPI.model.User;
import com.recetasAPI.repository.UserRepository;
import com.recetasAPI.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public Mono<User> crearUsuario(User user) {
        user.setCreatedAt(LocalDateTime.now());

        return userRepository.save(user);
    }
}
