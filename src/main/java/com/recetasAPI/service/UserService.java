package com.recetasAPI.service;

import com.recetasAPI.model.User;
import reactor.core.publisher.Mono;

public interface UserService {

    Mono<User> crearUsuario(User user);
}
