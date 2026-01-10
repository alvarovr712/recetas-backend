package com.recetasAPI.service;

import com.recetasAPI.model.dtos.LoginRequest;
import com.recetasAPI.model.dtos.UserInfoDTO;
import reactor.core.publisher.Mono;

public interface AuthService {

    Mono<String> login(LoginRequest loginRequest,String ip,String browser);
    Mono<UserInfoDTO> getCurrentUserInfo();
}
