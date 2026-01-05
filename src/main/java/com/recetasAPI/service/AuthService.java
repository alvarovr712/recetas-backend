package com.recetasAPI.service;

import com.recetasAPI.model.dtos.LoginRequest;
import reactor.core.publisher.Mono;

public interface AuthService {

    Mono<String> login(LoginRequest loginRequest,String ip,String browser);
}
