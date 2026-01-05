package com.recetasAPI.serviceImpl;

import com.recetasAPI.model.User;
import com.recetasAPI.model.enums.Role;
import com.recetasAPI.repository.UserRepository;
import com.recetasAPI.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Override
    public Mono<User> crearUsuario(User user) {
        user.setCreatedAt(LocalDateTime.now());
        user.setEnabled(false);
        user.setRole(Role.USER);

        return userRepository.findByEmail(user.getEmail())
                .flatMap(existing -> Mono.<User>error(new RuntimeException("El email ya existe")))
                .switchIfEmpty(
                        userRepository.findByUsername(user.getUsername())
                                .flatMap(existing -> Mono.<User>error(new RuntimeException("El username ya existe")))
                                .switchIfEmpty(Mono.defer(() -> {
                                    // Encriptamos la contraseña antes de guardar
                                    user.setPassword(passwordEncoder.encode(user.getPassword()));
                                    return userRepository.save(user);
                                })));
    }

}
