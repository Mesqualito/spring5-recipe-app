package com.eigenbaumarkt.spring5recipeapp.converters;

import com.eigenbaumarkt.spring5recipeapp.commands.CategoryCommand;
import com.eigenbaumarkt.spring5recipeapp.commands.IngredientCommand;
import com.eigenbaumarkt.spring5recipeapp.commands.NotesCommand;
import com.eigenbaumarkt.spring5recipeapp.commands.RecipeCommand;
import com.eigenbaumarkt.spring5recipeapp.domain.Difficulty;
import com.eigenbaumarkt.spring5recipeapp.domain.Recipe;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class RecipeCommandToRecipeTest {

    public static final Long ID_VALUE = new Long(111L);
    public static final Integer COOK_TIME = Integer.valueOf("5");
    public static final Integer PREP_TIME = Integer.valueOf("7");
    public static final String DESCRIPTION = "A wonderful Meal";
    public static final String DIRECTIONS = "Directions: eher \"Gebrauchsanleitung\" als \"Richtung\"!";
    public static final Difficulty DIFFICULTY = Difficulty.EASY;
    public static final Integer SERVINGS = Integer.valueOf("4");
    public static final String SOURCE = "Where do I come from?";
    public static final String URL = "https://www.gebsattel.rocks";
    public static final Long CAT_ID_1 = new Long(1L);
    public static final Long CAT_ID_2 = new Long(2L);
    public static final Long INGRED_ID_1 = new Long(3L);
    public static final Long INGRED_ID_2 = new Long(4L);
    public static final Long NOTES_ID = new Long(5L);

    RecipeCommandToRecipe converter;

    @Before
    public void setUp() throws Exception {
        converter = new RecipeCommandToRecipe(new CategoryCommandToCategory(),
                new IngredientCommandToIngredient(new UnitOfMeasureCommandToUnitOfMeasure()),
                new NotesCommandToNotes());
    }

    @Test
    public void testNullObject() throws Exception {
        assertNull(converter.convert(null));
    }

    @Test
    public void testEmptyObject() throws Exception {
        assertNotNull(converter.convert(new RecipeCommand()));
    }

    @Test
    public void convert() throws Exception {

        //given
        RecipeCommand recipeCommand = new RecipeCommand();
        recipeCommand.setId(ID_VALUE);
        recipeCommand.setCookTime(COOK_TIME);
        recipeCommand.setPrepTime(PREP_TIME);
        recipeCommand.setDescription(DESCRIPTION);
        recipeCommand.setDifficulty(DIFFICULTY);
        recipeCommand.setDirections(DIRECTIONS);
        recipeCommand.setServings(SERVINGS);
        recipeCommand.setSource(SOURCE);
        recipeCommand.setUrl(URL);

        NotesCommand notes = new NotesCommand();
        notes.setId(NOTES_ID);

        recipeCommand.setNotes(notes);

        CategoryCommand category = new CategoryCommand();
        category.setId(CAT_ID_1);

        CategoryCommand category2 = new CategoryCommand();
        category2.setId(CAT_ID_2);

        recipeCommand.getCategories().add(category);
        recipeCommand.getCategories().add(category2);

        IngredientCommand ingredient = new IngredientCommand();
        ingredient.setId(INGRED_ID_1);

        IngredientCommand ingredient2 = new IngredientCommand();
        ingredient2.setId(INGRED_ID_2);

        recipeCommand.getIngredients().add(ingredient);
        recipeCommand.getIngredients().add(ingredient2);

        //when
        Recipe recipe  = converter.convert(recipeCommand);

        assertNotNull(recipe);
        assertEquals(ID_VALUE, recipe.getId());
        assertEquals(COOK_TIME, recipe.getCookTime());
        assertEquals(PREP_TIME, recipe.getPrepTime());
        assertEquals(DESCRIPTION, recipe.getDescription());
        assertEquals(DIFFICULTY, recipe.getDifficulty());
        assertEquals(DIRECTIONS, recipe.getDirections());
        assertEquals(SERVINGS, recipe.getServings());
        assertEquals(SOURCE, recipe.getSource());
        assertEquals(URL, recipe.getUrl());
        assertEquals(NOTES_ID, recipe.getNotes().getId());
        assertEquals(2, recipe.getCategories().size());
        assertEquals(2, recipe.getIngredients().size());
    }
}
