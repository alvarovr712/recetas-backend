package com.recetasAPI.repository;

import com.recetasAPI.model.RecipeIngredient;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface RecipeIngredientRepository extends ReactiveMongoRepository<RecipeIngredient,String> {
}
