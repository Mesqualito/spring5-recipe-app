package com.eigenbaumarkt.spring5recipeapp.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity
public class Recipe {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String description;
    private Integer prepTime;
    private Integer cookTime;
    private Integer servings;
    private String source;
    private String url;

    @Lob
    private String directions;

    // EnumType.ORDINAL would persist the values
    // as 1, 2, 3... (not EASY, MEDIUM, HARD as EnumType.STRING)
    // ! Use STRING-value, because the values will survive even if
    // the sequence of the values in the Enum changes !
    @Enumerated(value = EnumType.STRING)
    private Difficulty difficulty;

    // @Lob tells Hibernate here to use a "Binary Large Object-" (BLOB-) field in the database
    // hibernate-Team is recommending using BLOB-field, not primitive type, because primitives can't be null
    // so use wrappers for the primitives!
    @Lob
    private Byte[] image;

    // the "owner" tells if and how to cascade deletes
    @OneToOne(cascade = CascadeType.ALL)
    private Notes notes;

    @ManyToMany
    private Set<Category> categories = new HashSet<>();

    // "Recipe"-Object is the "owner" or "parent" in
    // the bi-directional relation with "Ingredient"-Objects.
    // mappedBy names the property in the child class
    // which holds the "parent" recipe-object
    // this means: the set of ingredients of each object of type Recipe
    // will be a property called "recipe" in the "child" objects
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "recipe")
    private Set<Ingredient> ingredients = new HashSet<>();

    @ManyToMany
    @JoinTable(name = "recipe_category",
        joinColumns = @JoinColumn(name = "recipe_id"),
        inverseJoinColumns = @JoinColumn(name = "category_id"))
    public Set<Category> getCategories() {
        return categories;
    }

    public void setNotes(Notes notes) {
        if (notes != null) {
            this.notes = notes;
            notes.setRecipe(this);
        }
    }

    // convenience-Method, vereint die Logik an einem Punkt
    public Recipe addIngredient(Ingredient ingredient){
        ingredient.setRecipe(this);
        this.ingredients.add(ingredient);
        return this;
    }
}
