package com.eigenbaumarkt.spring5recipeapp.controllers;

import com.eigenbaumarkt.spring5recipeapp.commands.RecipeCommand;
import com.eigenbaumarkt.spring5recipeapp.services.RecipeService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class RecipeController {

    private final RecipeService recipeService;

    public RecipeController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    // change path to "REST-like" concept
    // das 'model' kommt von Spring MVC, die Eigenschaft 'recipe', ein 'Recipe'-Objekt wird mit der Methode 'findById()'
    // vom 'RecipeServiceImpl' geholt:
    @RequestMapping("/recipe/{id}/show")
    public String showById(@PathVariable String id, Model model){

        model.addAttribute("recipe", recipeService.findById(new Long(id)));

        // tell thymeleaf to use the new html-template 'show.html'
        return "recipe/show";
    }

    @RequestMapping("recipe/new")
    public String newRecipe(Model model) {
        model.addAttribute("recipe", new RecipeCommand());

        return "recipe/recipeform";
    }

    @RequestMapping("recipe/{id}/update")
    public String updateRecipe(@PathVariable String id, Model model){
        model.addAttribute("recipe", recipeService.findCommandById(Long.valueOf(id)));

        return "recipe/recipeform";
    }

    // if the server will receive a POST request via '[URL]/recipe', this method will be called
    @PostMapping
    @RequestMapping("recipe")
    // '@ModelAttribute' tells Spring to bind the form POST parameters
    // to the RecipeCommand-Object (by the naming conventions in the form)
    public String saveOrUpdate(@ModelAttribute RecipeCommand command) {
        RecipeCommand savedCommand = recipeService.saveRecipeCommand(command);

        // 'redirect:...' tells Spring to redirect to a specific URL
        return "redirect:/recipe/" + savedCommand.getId() + "/show";
    }
}
