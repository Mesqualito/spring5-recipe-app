package com.eigenbaumarkt.spring5recipeapp.services;

import com.eigenbaumarkt.spring5recipeapp.domain.Recipe;
import com.eigenbaumarkt.spring5recipeapp.repositories.RecipeRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

public class RecipeServiceImplTest {

    RecipeServiceImpl recipeService;

    // as we do not want to use a database or other dependencies in Unit tests,
    // we create a Mockito Mock as a RecipeRepository-object, a required dependency for testing the RecipeServiceImpl
    @Mock
    RecipeRepository recipeRepository;

    @Before
    public void setUp() throws Exception {
        // for this test, the above Mock recipeRepository will be initiated
        MockitoAnnotations.initMocks(this);
        recipeService = new RecipeServiceImpl(recipeRepository);
    }

    @Test
    public void getRecipes() {

        // fill the Mock-Object recipeRepository with one entry for the size()-test below
        Recipe recipe = new Recipe();
        HashSet recipesData = new HashSet();
        recipesData.add(recipe);

        // tell Mockito to use this as return value in its Mock-Object recipeRepository
        when(recipeRepository.findAll()).thenReturn(recipesData);

        // we need the recipeService from the setUp()-method which in turn needs the Mock recipeRepository
        // to be able to do our Unit test now
        Set<Recipe> recipes = recipeService.getRecipes();
        assertEquals(recipes.size(), 1);

        // we also want to verify the interaction with the Mock-Object;
        // we test, if the findAll()-method of the Mock-Object recipeRepository is called once (and only once)
        verify(recipeRepository, times(1)).findAll();
    }
}