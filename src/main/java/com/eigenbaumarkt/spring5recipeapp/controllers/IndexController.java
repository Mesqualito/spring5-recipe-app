package com.eigenbaumarkt.spring5recipeapp.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class IndexController {

    @RequestMapping({"", "/", "/index", "/index.htm", "/index.html"})
    public String getIndexPage(){

        return "index";
    }
}
