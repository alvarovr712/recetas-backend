package com.recetasAPI.controller;

import com.recetasAPI.model.User;
import com.recetasAPI.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.util.Map;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/crear")
    public Mono<ResponseEntity<Object>> createUser(@RequestBody User user) {
        return userService.crearUsuario(user)
                .map(savedUser -> ResponseEntity.status(HttpStatus.CREATED).<Object>body(savedUser))
                .onErrorResume(e -> {
                    e.printStackTrace(); // Log full stack trace for debugging
                    Map<String, String> errorBody = Map.of("error", e.getMessage());
                    return Mono.just(ResponseEntity.badRequest().<Object>body(errorBody));
                });
    }

}
