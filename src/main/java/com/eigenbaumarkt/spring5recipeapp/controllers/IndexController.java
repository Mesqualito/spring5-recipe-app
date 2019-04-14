package com.eigenbaumarkt.spring5recipeapp.controllers;

import com.eigenbaumarkt.spring5recipeapp.domain.Category;
import com.eigenbaumarkt.spring5recipeapp.domain.UnitOfMeasure;
import com.eigenbaumarkt.spring5recipeapp.repositories.CategoryRepository;
import com.eigenbaumarkt.spring5recipeapp.repositories.UnitOfMeasureRepository;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Optional;

@Controller
public class IndexController {

    private CategoryRepository categoryRepository;
    private UnitOfMeasureRepository unitOfMeasureRepository;

    public IndexController(CategoryRepository categoryRepository, UnitOfMeasureRepository unitOfMeasureRepository) {
        this.categoryRepository = categoryRepository;
        this.unitOfMeasureRepository = unitOfMeasureRepository;
    }

    @RequestMapping({"", "/", "/index", "/index.htm", "/index.html"})
    public String getIndexPage(){

        Optional<Category> categoryOptional = categoryRepository.findByDescription("Mexican");
        Optional<UnitOfMeasure> unitOfMeasureOptional = unitOfMeasureRepository.findByDescription("Teaspoon");


        System.out.println("Ein Spring Boot Development Tools reload test, sobald ein Build stattfindet. " +
                "Dabei werden zwei Classloader eingesetzt: die dependencies werden extra gehandhabt " +
                "und nicht refreshed.Die Klassen der eigenen Anwendung werden im zweiten Classloader " +
                "refreshed...");

        System.out.println("Category ID ist: " + categoryOptional.get().getId());
        System.out.println("UnitOfMeasure ID ist: " + unitOfMeasureOptional.get().getId());

        return "index";
    }
}
