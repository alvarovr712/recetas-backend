package com.recetasAPI.repository;

import com.recetasAPI.model.Ingredient;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface IngredientRepository extends ReactiveMongoRepository<Ingredient,String> {
}
