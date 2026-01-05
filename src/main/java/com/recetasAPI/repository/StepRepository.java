package com.recetasAPI.repository;

import com.recetasAPI.model.Step;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;

public interface StepRepository extends ReactiveMongoRepository<Step,String> {
    Flux<Step> findByRecipeIdOrderByOrderAsc(String recipeId);
}
