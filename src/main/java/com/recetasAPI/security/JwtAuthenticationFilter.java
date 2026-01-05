package com.recetasAPI.security;

import com.recetasAPI.model.User;
import com.recetasAPI.repository.UserRepository;
import org.springframework.http.HttpCookie;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

import java.util.List;

@Component
public class JwtAuthenticationFilter implements WebFilter {

    private final JwtUtil jwtUtil;
    private final UserRepository userRepository;

    public JwtAuthenticationFilter(JwtUtil jwtUtil, UserRepository userRepository) {
        this.jwtUtil = jwtUtil;
        this.userRepository = userRepository;
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        HttpCookie jwtCookie = request.getCookies().getFirst("JWT");

        if (jwtCookie == null) {
            return chain.filter(exchange);
        }

        String token = jwtCookie.getValue();

        if (!jwtUtil.validateToken(token)) {
            return chain.filter(exchange);
        }

        String userId = jwtUtil.getUserIdFromToken(token);

        return userRepository.findById(userId)
                .flatMap(user -> {
                    List<SimpleGrantedAuthority> authorities = List.of(
                            new SimpleGrantedAuthority("ROLE_" + user.getRole().name())
                    );

                    Authentication auth = new UsernamePasswordAuthenticationToken(user.getUsername(), null, authorities);

                    // Aquí usamos la forma correcta para escribir el contexto reactivo
                    return chain.filter(exchange)
                            .contextWrite(ReactiveSecurityContextHolder.withAuthentication(auth));
                })
                .switchIfEmpty(chain.filter(exchange));
    }
}
