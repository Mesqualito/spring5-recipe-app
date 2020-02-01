package com.eigenbaumarkt.spring5recipeapp.services;

import com.eigenbaumarkt.spring5recipeapp.commands.IngredientCommand;
import com.eigenbaumarkt.spring5recipeapp.converters.IngredientCommandToIngredient;
import com.eigenbaumarkt.spring5recipeapp.converters.IngredientToIngredientCommand;
import com.eigenbaumarkt.spring5recipeapp.domain.Ingredient;
import com.eigenbaumarkt.spring5recipeapp.domain.Recipe;
import com.eigenbaumarkt.spring5recipeapp.repositories.RecipeRepository;
import com.eigenbaumarkt.spring5recipeapp.repositories.UnitOfMeasureRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Slf4j
@Service
public class IngredientServiceImpl implements IngredientService {

    private final RecipeRepository recipeRepository;
    private final IngredientToIngredientCommand ingredientToIngredientCommand;
    private final IngredientCommandToIngredient ingredientCommandToIngredient;
    private final UnitOfMeasureRepository unitOfMeasureRepository;

    public IngredientServiceImpl(IngredientToIngredientCommand ingredientToIngredientCommand,
                                 RecipeRepository recipeRepository, IngredientCommandToIngredient ingredientCommandToIngredient, UnitOfMeasureRepository unitOfMeasureRepository) {
        this.ingredientToIngredientCommand = ingredientToIngredientCommand;
        this.recipeRepository = recipeRepository;
        this.ingredientCommandToIngredient = ingredientCommandToIngredient;
        this.unitOfMeasureRepository = unitOfMeasureRepository;
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

    @Override
    @Transactional
    public IngredientCommand saveIngredientCommand(IngredientCommand command) {
        Optional<Recipe> recipeOptional = recipeRepository.findById(command.getRecipeId());

        if(!recipeOptional.isPresent()) {
            // TODO: toss error if not found!
            log.error("Recipe not found for id: " + command.getRecipeId());
            return new IngredientCommand();
        } else {
            Recipe recipe = recipeOptional.get();

            Optional<Ingredient> ingredientOptional = recipe.getIngredients().stream()
                    .filter(ingredient -> ingredient.getId().equals(command.getId()))
                    .findFirst();
            if(ingredientOptional.isPresent()){
                Ingredient ingredientFound = ingredientOptional.get();
                ingredientFound.setDescription(command.getDescription());
                ingredientFound.setAmount(command.getAmount());
                ingredientFound.setUom(unitOfMeasureRepository.findById(command.getUom().getId())
                .orElseThrow(() -> new RuntimeException("Unit of Measure NOT FOUND"))); // TODO: address this
            } else {
                // add new ingredient
                recipe.addIngredient(ingredientCommandToIngredient.convert(command));
            }

            Recipe savedRecipe = recipeRepository.save(recipe);

            Optional<Ingredient> savedIngredientOptional = savedRecipe.getIngredients().stream()
                    .filter(recipeIngredients -> recipeIngredients.getId().equals(command.getId()))
                    .findFirst();

            if(!savedIngredientOptional.isPresent()){
                // check by id not possible - a new Ingredient doesn't have an id yet
                // we check other properties - not totally save, but best guess
                savedIngredientOptional = savedRecipe.getIngredients().stream()
                        .filter(recipeIngredients -> recipeIngredients.getDescription().equals(command.getDescription()))
                        .filter(recipeIngredients -> recipeIngredients.getAmount().equals(command.getAmount()))
                        .filter(recipeIngredients -> recipeIngredients.getUom().getId().equals(command.getUom().getId()))
                        .findFirst();

                // TODO: check for fail
                return ingredientToIngredientCommand.convert(savedIngredientOptional.get());

            }

            return ingredientToIngredientCommand.convert(savedRecipe.getIngredients().stream()
            .filter(recipeIngredients -> recipeIngredients.getId().equals(command.getId()))
            .findFirst()
            .get());
        }
    }

    @Override
    public void deleteById(Long recipeId, Long id) {

        log.debug("Deleting ingredient: " + recipeId + " : " + id);

        Optional<Recipe> recipeOptional = recipeRepository.findById(recipeId);

        if(recipeOptional.isPresent()){
            Recipe recipe = recipeOptional.get();
            log.debug("found recipe");

            Optional<Ingredient> ingredientOptional = recipe.getIngredients().stream()
                    .filter(ingredient -> ingredient.getId().equals(id))
                    .findFirst();

            if(ingredientOptional.isPresent()) {
                log.debug("found ingredient");
                Ingredient ingredient = ingredientOptional.get();
                // will tell hibernate to delete relation from the database
                ingredient.setRecipe(null);
                recipe.getIngredients().remove(ingredient);
                recipeRepository.save(recipe);
            }
        } else {
            // TODO: error-handling has to be implemented
            log.debug("Recipe Id not found. Id: " + recipeId);
        }
    }
}
