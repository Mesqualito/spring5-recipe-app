package com.eigenbaumarkt.spring5recipeapp.domain;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
public class Ingredient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String description;
    private BigDecimal amount;

    // uni-directional one-to-one relationship
    // FetchType.EAGER: tells Hibernate to fetch
    // the related object every time
    @OneToOne(fetch = FetchType.EAGER)
    private UnitOfMeasure measUnit;

    // building a bi-directional relationship
    // Cascading is defined by the owner of the
    // relationship
    @ManyToOne
    private Recipe recipe;

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

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public Recipe getRecipe() {
        return recipe;
    }

    public void setRecipe(Recipe recipe) {
        this.recipe = recipe;
    }

    public UnitOfMeasure getMeasUnit() {
        return measUnit;
    }

    public void setMeasUnit(UnitOfMeasure measUnit) {
        this.measUnit = measUnit;
    }
}