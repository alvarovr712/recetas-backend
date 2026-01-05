package com.recetasAPI.serviceImpl;

import com.recetasAPI.repository.IngredientRepository;
import com.recetasAPI.service.IngredientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class IngredientServiceImpl implements IngredientService {

    @Autowired
    private IngredientRepository ingredientRepository;
}
