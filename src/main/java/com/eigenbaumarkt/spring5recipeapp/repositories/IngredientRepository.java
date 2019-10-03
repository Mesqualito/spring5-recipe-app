package com.eigenbaumarkt.spring5recipeapp.repositories;

import com.eigenbaumarkt.spring5recipeapp.domain.Ingredient;
import org.springframework.data.repository.CrudRepository;

public interface IngredientRepository extends CrudRepository<Ingredient, Long> {
}
