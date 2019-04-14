package com.eigenbaumarkt.spring5recipeapp.services;

import com.eigenbaumarkt.spring5recipeapp.domain.Recipe;

import java.util.Set;

public interface RecipeService {

    Set<Recipe> getRecipes();


}
