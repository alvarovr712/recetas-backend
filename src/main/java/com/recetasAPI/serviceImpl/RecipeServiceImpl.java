package com.recetasAPI.serviceImpl;

import com.recetasAPI.repository.RecipeRepository;
import com.recetasAPI.service.RecipeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RecipeServiceImpl implements RecipeService {

    @Autowired
    private RecipeRepository recipeRepository;
}
