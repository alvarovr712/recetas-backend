package com.recetasAPI.serviceImpl;

import com.recetasAPI.model.Session;
import com.recetasAPI.model.dtos.LoginRequest;
import com.recetasAPI.model.dtos.UserInfoDTO;
import com.recetasAPI.model.enums.Role;
import com.recetasAPI.repository.SessionRepository;
import com.recetasAPI.repository.UserRepository;
import com.recetasAPI.security.JwtUtil;
import com.recetasAPI.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

@Service
public class AuthServiceImpl implements AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private SessionRepository sessionRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtil jwtUtil;

    @Override
    public Mono<String> login(LoginRequest loginRequest, String ip, String browser) {
        return userRepository
                .findByUsernameOrEmail(loginRequest.getUsernameOrEmail(), loginRequest.getUsernameOrEmail())
                .switchIfEmpty(Mono.error(new RuntimeException("Usuario no encontrado")))
                .flatMap(user -> {
                    if (!passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())) {
                        return Mono.error(new RuntimeException("Contraseña incorrecta"));
                    }
                    if (!user.getEnabled()) {
                        return Mono.error(new RuntimeException("Usuario no activado"));
                    }

                    String token = jwtUtil.generateToken(user.getId());

                    // Crear sesión
                    Session session = new Session();
                    session.setUserId(user.getId());
                    session.setCreatedAt(LocalDateTime.now());
                    session.setExpiresAt(LocalDateTime.now().plusWeeks(1));
                    session.setIp(ip);
                    session.setBrowser(browser);
                    session.setToken(token);
                    session.setEnabled(true);

                    return sessionRepository.save(session)
                            .thenReturn(token);
                });
    }

    @Override
    public Mono<UserInfoDTO> getCurrentUserInfo() {
        return ReactiveSecurityContextHolder.getContext()
                .map(SecurityContext::getAuthentication)
                .filter(auth -> auth != null && auth.isAuthenticated())
                .map(auth -> {
                    String username = auth.getName();
                    String roleStr = auth.getAuthorities().stream()
                            .findFirst()
                            .map(ga -> ga.getAuthority().replace("ROLE_", ""))
                            .orElse("USER");

                    return new UserInfoDTO(username, Role.valueOf(roleStr));
                });
    }

    @Override
    public Mono<Void> logout(String token) {
        return sessionRepository.findByTokenAndEnabledTrue(token)
                .flatMap(session -> {
                    session.setEnabled(false);
                    session.setExpiresAt(LocalDateTime.now());
                    return sessionRepository.save(session);
                })
                .then();
    }
}
