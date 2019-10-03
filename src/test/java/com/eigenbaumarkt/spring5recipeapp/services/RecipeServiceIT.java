package com.eigenbaumarkt.spring5recipeapp.services;

import com.eigenbaumarkt.spring5recipeapp.commands.RecipeCommand;
import com.eigenbaumarkt.spring5recipeapp.converters.RecipeCommandToRecipe;
import com.eigenbaumarkt.spring5recipeapp.converters.RecipeToRecipeCommand;
import com.eigenbaumarkt.spring5recipeapp.domain.Recipe;
import com.eigenbaumarkt.spring5recipeapp.repositories.RecipeRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
// not using only '@DataJpaTest', because it will miss the 'RecipeService' etc.
// '@SpringBootTest' will use the whole Spring Configuration
@SpringBootTest
public class RecipeServiceIT {

    public static final String NEW_DESCRIPTION = "New Description";

    @Autowired
    RecipeService recipeService;

    @Autowired
    RecipeRepository recipeRepository;

    @Autowired
    RecipeCommandToRecipe recipeCommandToRecipe;

    @Autowired
    RecipeToRecipeCommand recipeToRecipeCommand;

    // da die Entitäten außerhalb des "transactional context" verwendet und lazely loaded properties abgefragt
    // werden muss mit '@Transactional' annotiert werden
    @Transactional
    @Test
    public void testSaveOfDescription() throws Exception {

        // behaviour driven development "given - then - when"

        // given
        // findet alle mit der bootstrap-Klasse gespeicherten Rezepte:
        Iterable<Recipe> recipes = recipeRepository.findAll();
        // zum Test wird das erste gefundene Rezept verwendet:
        Recipe testRecipe = recipes.iterator().next();
        // 'testRecipeCommand' ist das POJO, das auch vom Webfrontend so angeliefert werden würde:
        RecipeCommand testRecipeCommand = recipeToRecipeCommand.convert(testRecipe);

        // when
        // jetzt werden die POJO-Daten verändert...
        testRecipeCommand.setDescription(NEW_DESCRIPTION);
        // ...und mit Spring Data JPA wieder in der Datenbank abgelegt, sowie gleichzeitig als neues, aus den
        // Datenbankeinträgen erstelltes Command-Object in der Variable 'savedRecipeCommand' zurück geliefert:
        RecipeCommand savedRecipeCommand = recipeService.saveRecipeCommand(testRecipeCommand);

        // then
        assertEquals(NEW_DESCRIPTION, savedRecipeCommand.getDescription());
        assertEquals(testRecipe.getId(), savedRecipeCommand.getId());
        assertEquals(testRecipe.getCategories().size(), savedRecipeCommand.getCategories().size());
        assertEquals(testRecipe.getIngredients().size(), savedRecipeCommand.getIngredients().size());
    }
}
