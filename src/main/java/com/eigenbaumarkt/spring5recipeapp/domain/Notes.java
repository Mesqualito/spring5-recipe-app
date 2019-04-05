package com.eigenbaumarkt.spring5recipeapp.domain;

import javax.persistence.*;

@Entity
public class Notes {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // if recipe-Object is deleted, the 1-to-1-related notes also will be deleted
    // if notes-object is deleted, the 1-to-1-related recipe doesn't care
    @OneToOne
    private Recipe recipe;

    // we will store longer than 255-char-Strings,
    // @Lob tells Hibernate here to use a "Character Large Object-" (CLOB-) field in the database
    @Lob
    private String recipeNotes;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Recipe getRecipe() {
        return recipe;
    }

    public void setRecipe(Recipe recipe) {
        this.recipe = recipe;
    }

    public String getRecipeNotes() {
        return recipeNotes;
    }

    public void setRecipeNotes(String recipeNotes) {
        this.recipeNotes = recipeNotes;
    }
}
