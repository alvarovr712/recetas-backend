package com.recetasAPI.controller;

import com.recetasAPI.service.RecipeIngredientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/recipeIngredient")
public class RecipeIngredientController {

    @Autowired
    private RecipeIngredientService recipeIngredientService;
}
