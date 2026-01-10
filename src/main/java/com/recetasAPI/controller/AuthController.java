package com.recetasAPI.controller;

import com.recetasAPI.model.dtos.LoginRequest;
import com.recetasAPI.model.dtos.UserInfoDTO;
import com.recetasAPI.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/auth")
public class AuthController {

        @Autowired
        private AuthService authService;

        @PostMapping("/login")
        public Mono<ResponseEntity<String>> login(@RequestBody LoginRequest loginRequest, ServerWebExchange exchange) {
                // Obtener IP y navegador (User-Agent)
                String ip = exchange.getRequest().getRemoteAddress() != null
                                ? exchange.getRequest().getRemoteAddress().getAddress().getHostAddress()
                                : "unknown";

                String browser = exchange.getRequest().getHeaders().getFirst("User-Agent");

                return authService.login(loginRequest, ip, browser)
                                .map(token -> {
                                        // Crear cookie con JWT
                                        ResponseCookie cookie = ResponseCookie.from("JWT", token)
                                                        .httpOnly(true)
                                                        .secure(true) // usar solo en HTTPS
                                                        .path("/")
                                                        .maxAge(2 * 60 * 60) // 2 horas en segundos
                                                        .sameSite("Strict")
                                                        .build();

                                        return ResponseEntity.ok()
                                                        .header(HttpHeaders.SET_COOKIE, cookie.toString())
                                                        .body("Login exitoso");
                                })
                                .onErrorResume(ex -> Mono.just(
                                                ResponseEntity.badRequest().body(ex.getMessage())));
        }

        @GetMapping("/me")
        public Mono<ResponseEntity<UserInfoDTO>> getCurrentUser() {
                return authService.getCurrentUserInfo()
                                .map(ResponseEntity::ok)
                                .defaultIfEmpty(ResponseEntity.status(401).build());
        }
}
