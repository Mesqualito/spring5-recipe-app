package com.eigenbaumarkt.spring5recipeapp.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class IndexController {

    @RequestMapping({"", "/", "/index", "/index.htm", "/index.html"})
    public String getIndexPage(){

        System.out.println("Ein Spring Boot Development Tools reload test, sobald ein Build stattfindet. " +
                "Dabei werden zwei Classloader eingesetzt: die dependencies werden extra gehandhabt " +
                "und nicht refreshed.Die Klassen der eigenen Anwendung werden im zweiten Classloader " +
                "refreshed...");
        return "index";
    }
}
