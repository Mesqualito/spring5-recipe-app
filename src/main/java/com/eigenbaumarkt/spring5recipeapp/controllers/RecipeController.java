package com.eigenbaumarkt.spring5recipeapp.controllers;

import com.eigenbaumarkt.spring5recipeapp.commands.RecipeCommand;
import com.eigenbaumarkt.spring5recipeapp.exceptions.NotFoundException;
import com.eigenbaumarkt.spring5recipeapp.services.RecipeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Slf4j
@Controller
public class RecipeController {

    private final RecipeService recipeService;

    public RecipeController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    @GetMapping("/recipe/{id}/show")
    // change path to "REST-like" concept
    // das 'model' kommt von Spring MVC, die Eigenschaft 'recipe', ein 'Recipe'-Objekt wird mit der Methode 'findById()'
    // vom 'RecipeServiceImpl' geholt:
    public String showById(@PathVariable String id, Model model) {

        model.addAttribute("recipe", recipeService.findById(new Long(id)));

        return "recipe/show";
    }

    @GetMapping("recipe/new")
    public String newRecipe(Model model) {
        model.addAttribute("recipe", new RecipeCommand());

        return "recipe/recipeform";
    }

    @GetMapping("recipe/{id}/update")
    public String updateRecipe(@PathVariable String id, Model model) {
        model.addAttribute("recipe", recipeService.findCommandById(Long.valueOf(id)));

        return "recipe/recipeform";
    }

    // if the server will receive a POST request via '[URL]/recipe', this method will be called
    @PostMapping("recipe")
    // '@ModelAttribute' tells Spring to bind the form POST parameters
    // to the RecipeCommand-Object (by the naming conventions in the form)
    public String saveOrUpdate(@ModelAttribute RecipeCommand command) {
        RecipeCommand savedCommand = recipeService.saveRecipeCommand(command);

        // 'redirect:...' tells Spring to redirect to a specific URL
        return "redirect:/recipe/" + savedCommand.getId() + "/show";
    }

    @GetMapping("recipe/{id}/delete")
    public String deleteById(@PathVariable String id) {
        log.debug("Deleting id: " + id);

        recipeService.deleteById(Long.valueOf(id));
        return "redirect:/";
    }

    // Spring MVC implementation of resolving HTML errors:
    // ExceptionHandlerExceptionResolver (extending the interface HandlerExceptionResolver), which matches
    // uncaught exceptions to @ExceptionHandler (use in Controllers!) and can be used with custom "NotFoundException.class"
    // and custom Thymeleaf template "404error.html"
    @ResponseStatus(HttpStatus.NOT_FOUND) // Attention: "higher precedence than exception-class" (??)
    @ExceptionHandler(NotFoundException.class)
    public ModelAndView handleNotFound(Exception exception) {

        log.error("Handling not found exception");
        log.error(exception.getMessage());

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("recipe/404error"); // must match Thymeleaf-template (here: "404error.html")
        modelAndView.addObject("exception", exception);

        return modelAndView;
    }
}
