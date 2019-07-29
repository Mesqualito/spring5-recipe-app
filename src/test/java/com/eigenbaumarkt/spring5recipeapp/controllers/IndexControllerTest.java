package com.eigenbaumarkt.spring5recipeapp.controllers;

import com.eigenbaumarkt.spring5recipeapp.services.RecipeService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.ui.Model;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.anySet;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class IndexControllerTest {

    @Mock
    RecipeService recipeService;

    @Mock
    Model model;

    IndexController controller;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        controller = new IndexController(recipeService);
        }

    @Test
    public void getIndexPage() throws Exception {
        String viewName = controller.getIndexPage(model);

        // Unit test of class IndexController and its method getIndexPage()
        assertEquals("index", viewName);

        // test interaction on Mock-Objects
        verify(recipeService, times(1)).getRecipes();
        // see IndexController-class to find the attributes name and compare it with Mockito static method anySet().
        // "Mockito extends ArgumentMatchers so to get access to all matchers just import Mockito class statically."
        verify(model, times(1)).addAttribute(eq("recipes"), anySet());

    }
}
