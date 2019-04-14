package com.eigenbaumarkt.spring5recipeapp.services;

import com.eigenbaumarkt.spring5recipeapp.domain.Recipe;
import com.eigenbaumarkt.spring5recipeapp.repositories.RecipeRepository;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class RecipeServiceImpl implements RecipeService {

    // use "final"
    private final RecipeRepository recipeRepository;

    public RecipeServiceImpl(RecipeRepository recipeRepository) {
        this.recipeRepository = recipeRepository;
    }

    @Override
    public Set<Recipe> getRecipes() {
        Set<Recipe> recipeSet = new HashSet<>();

        // since Java 8
        recipeRepository.findAll().iterator().forEachRemaining(recipeSet::add);

        return recipeSet;
    }
}
