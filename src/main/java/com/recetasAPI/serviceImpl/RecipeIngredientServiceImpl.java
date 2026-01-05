package com.recetasAPI.serviceImpl;

import com.recetasAPI.repository.RecipeIngredientRepository;
import com.recetasAPI.service.RecipeIngredientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RecipeIngredientServiceImpl implements RecipeIngredientService {

    @Autowired
    private RecipeIngredientRepository recipeIngredientRepository;
}
