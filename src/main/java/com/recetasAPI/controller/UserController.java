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

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/crear")
    public Mono<ResponseEntity<User>> createUser(@RequestBody User user) {
        return userService.crearUsuario(user)
                .map(savedUser ->
                        ResponseEntity
                                .status(HttpStatus.CREATED)
                                .body(savedUser)
                );
    }

}
