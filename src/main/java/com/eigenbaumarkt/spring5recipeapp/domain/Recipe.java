package com.eigenbaumarkt.spring5recipeapp.domain;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getPrepTime() {
        return prepTime;
    }

    public void setPrepTime(Integer prepTime) {
        this.prepTime = prepTime;
    }

    public Integer getCookTime() {
        return cookTime;
    }

    public void setCookTime(Integer cookTime) {
        this.cookTime = cookTime;
    }

    public Integer getServings() {
        return servings;
    }

    public void setServings(Integer servings) {
        this.servings = servings;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getDirections() {
        return directions;
    }

    public void setDirections(String directions) {
        this.directions = directions;
    }

    public Difficulty getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(Difficulty difficulty) {
        this.difficulty = difficulty;
    }

    public Byte[] getImage() {
        return image;
    }

    public void setImage(Byte[] image) {
        this.image = image;
    }

    public Notes getNotes() {
        return notes;
    }

    public void setNotes(Notes notes) {
        this.notes = notes;
    }

    public Set<Ingredient> getIngredients() {
        return ingredients;
    }

    public void setIngredients(Set<Ingredient> ingredients) {
        this.ingredients = ingredients;
    }

    @ManyToMany
    @JoinTable(name = "recipe_category",
        joinColumns = @JoinColumn(name = "recipe_id"),
        inverseJoinColumns = @JoinColumn(name = "category_id"))
    public Set<Category> getCategories() {
        return categories;
    }

    public void setCategories(Set<Category> categories) {
        this.categories = categories;
    }
}
