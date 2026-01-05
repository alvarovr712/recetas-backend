package com.recetasAPI.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "recipesingredients")
public class RecipeIngredient {

    @Id
    private String id;
    private String ingredientId;
    private String recipeId;
    private double quantity;
}
