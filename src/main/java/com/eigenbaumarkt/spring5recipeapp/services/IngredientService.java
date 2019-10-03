package com.eigenbaumarkt.spring5recipeapp.services;

import com.eigenbaumarkt.spring5recipeapp.domain.Ingredient;

import java.util.Set;

public interface IngredientService {

    Set<Ingredient> getIngredients();
}
