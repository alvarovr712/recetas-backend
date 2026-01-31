package com.recetasAPI.repository;

import com.recetasAPI.model.Session;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface SessionRepository extends ReactiveMongoRepository<Session,String> {
    Flux<Session> findByUserId(String userId);
    Mono<Session> findByTokenAndEnabledTrue(String token);
}
