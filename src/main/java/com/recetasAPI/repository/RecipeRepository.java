package com.recetasAPI.repository;

import com.recetasAPI.model.Recipe;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface RecipeRepository extends ReactiveMongoRepository<Recipe,String> {
}
