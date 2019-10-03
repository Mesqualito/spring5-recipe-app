package com.eigenbaumarkt.spring5recipeapp.services;

import com.eigenbaumarkt.spring5recipeapp.commands.IngredientCommand;
import com.eigenbaumarkt.spring5recipeapp.converters.IngredientToIngredientCommand;
import com.eigenbaumarkt.spring5recipeapp.domain.Recipe;
import com.eigenbaumarkt.spring5recipeapp.repositories.RecipeRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
public class IngredientServiceImpl implements IngredientService {

    private final RecipeRepository recipeRepository;
    private final IngredientToIngredientCommand ingredientToIngredientCommand;

    public IngredientServiceImpl(IngredientToIngredientCommand ingredientToIngredientCommand, RecipeRepository recipeRepository) {
        this.ingredientToIngredientCommand = ingredientToIngredientCommand;
        this.recipeRepository = recipeRepository;
    }

    @Override
    public IngredientCommand findByRecipeIdAndIngredientId(Long recipeId, Long ingredientId) {

        Optional<Recipe> recipeOptional = recipeRepository.findById(recipeId);
        if (!recipeOptional.isPresent()){
            // TODO: Implementierung error-handling: => "404.html"
            log.error("recipe id " + recipeId + " not found!");
        }

        Recipe recipe = recipeOptional.get();

        // Nutzung von JAVA-Streams; default return: Optional !
        Optional<IngredientCommand> ingredientCommandOptional = recipe.getIngredients().stream()
                .filter(ingredient ->  ingredient.getId().equals(ingredientId))
                .map(ingredientToIngredientCommand::convert).findFirst();
                // "method-reference" ersetzt Lambda-Ausdruck :
                // .map(ingredient -> ingredientToIngredientCommand.convert(ingredient)).findFirst();

        if(!ingredientCommandOptional.isPresent()) {
            // TODO: Implementierung error-handling: => "404.html"
            log.error("Ingredient id " + ingredientId + " not found!");
    }

        return ingredientCommandOptional.get();
    }
}
