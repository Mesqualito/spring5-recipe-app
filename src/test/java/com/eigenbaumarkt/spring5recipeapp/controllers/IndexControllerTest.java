package com.eigenbaumarkt.spring5recipeapp.controllers;

import com.eigenbaumarkt.spring5recipeapp.domain.Recipe;
import com.eigenbaumarkt.spring5recipeapp.services.RecipeService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.ui.Model;

import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

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

        // given
        Set<Recipe> recipes = new HashSet<>();
        Recipe recipe1 = new Recipe();
        recipe1.setId(1L);
        Recipe recipe2 = new Recipe();
        recipe1.setId(2L);
        recipes.add(recipe1);
        recipes.add(recipe2);

        ArgumentCaptor<Set<Recipe>> argumentCaptor = ArgumentCaptor.forClass(Set.class);

        // when
        when(recipeService.getRecipes()).thenReturn(recipes);
        String viewName = controller.getIndexPage(model);

        // then
        // Unit test of class IndexController and its method getIndexPage()
        assertEquals("index", viewName);

        // test interaction on Mock-Objects
        verify(recipeService, times(1)).getRecipes();
        verify(model, times(1)).addAttribute(eq("recipes"), argumentCaptor.capture());
        Set<Recipe> setInController = argumentCaptor.getValue();
        assertEquals(2, setInController.size());
    }

    // testing controller can be tricky (tests of response codes, media types, request path mapping etc.)
    // Spring MockMVC helps unit testing with a Mock servlet context - without having to start the
    // heavyweight Spring context.
    // This way we can use a Mock web server or Mock dispatcher server and the test around controllers become
    // very lightweight.
    @Test
    public void testMockMVC() throws Exception {
        // with standaloneSetup() we have an Unit test with a "full knife API",
        // webAppContextSetup() brings up the Spring framework context, so it would become an Integration test
        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(controller).build();

        // get() from org.springframework.test.web.servlet.MockMvcBuilder;
        mockMvc.perform(get("/"))
                .andExpect(status().isOk()) // get back a 200 status from controller
                .andExpect(view().name("index"));
    }
}
