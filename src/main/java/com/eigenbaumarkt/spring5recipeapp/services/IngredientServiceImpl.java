package com.eigenbaumarkt.spring5recipeapp.services;

import com.eigenbaumarkt.spring5recipeapp.converters.IngredientCommandToIngredient;
import com.eigenbaumarkt.spring5recipeapp.converters.IngredientToIngredientCommand;
import com.eigenbaumarkt.spring5recipeapp.domain.Ingredient;
import com.eigenbaumarkt.spring5recipeapp.repositories.IngredientRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Slf4j
@Service
public class IngredientServiceImpl implements IngredientService {

    private final IngredientRepository ingredientRepository;
    private final IngredientCommandToIngredient ingredientCommandToIngredient;
    private final IngredientToIngredientCommand ingredientToIngredientCommand;

    public IngredientServiceImpl(IngredientRepository ingredientRepository, IngredientCommandToIngredient ingredientCommandToIngredient, IngredientToIngredientCommand ingredientToIngredientCommand) {
        this.ingredientRepository = ingredientRepository;
        this.ingredientCommandToIngredient = ingredientCommandToIngredient;
        this.ingredientToIngredientCommand = ingredientToIngredientCommand;
    }

    @Override
    public Set<Ingredient> getIngredients() {
        log.debug("I'm in the Ingredient-Service");

        Set<Ingredient> ingredientSet = new HashSet<>();
        ingredientRepository.findAll().iterator().forEachRemaining(ingredientSet::add);
        return ingredientSet;
    }
}
