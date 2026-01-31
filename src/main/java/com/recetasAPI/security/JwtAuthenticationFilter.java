package com.recetasAPI.security;

import com.recetasAPI.repository.UserRepository;
import org.springframework.http.HttpCookie;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

import java.util.List;

public class JwtAuthenticationFilter implements WebFilter {

    private final JwtUtil jwtUtil;
    private final UserRepository userRepository;

    public JwtAuthenticationFilter(JwtUtil jwtUtil, UserRepository userRepository) {
        this.jwtUtil = jwtUtil;
        this.userRepository = userRepository;
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        HttpCookie jwtCookie = exchange.getRequest().getCookies().getFirst("JWT");

        if (jwtCookie == null) {
            return chain.filter(exchange);
        }

        String token = jwtCookie.getValue();
        if (!jwtUtil.validateToken(token)) {
            return chain.filter(exchange);
        }

        String userId = jwtUtil.getUserIdFromToken(token);

        // Usamos una estructura que evita la doble ejecución de chain.filter
        // Mono<Void> es tratado como "vacío" por switchIfEmpty, lo cual causaba el
        // error anterior.
        return userRepository.findById(userId)
                .flatMap(user -> {
                    Authentication auth = new UsernamePasswordAuthenticationToken(
                            user.getUsername(),
                            null,
                            List.of(new SimpleGrantedAuthority("ROLE_" + user.getRole().name())));

                    return chain.filter(exchange)
                            .contextWrite(ReactiveSecurityContextHolder.withAuthentication(auth))
                            .thenReturn(true); // Emitimos algo para que no se considere vacío
                })
                .defaultIfEmpty(false)
                .flatMap(alreadyProcessed -> {
                    if (alreadyProcessed instanceof Boolean && (Boolean) alreadyProcessed) {
                        return Mono.empty();
                    }
                    return chain.filter(exchange);
                });
    }
}
