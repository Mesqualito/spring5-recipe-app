package com.eigenbaumarkt.spring5recipeapp.repositories;

import com.eigenbaumarkt.spring5recipeapp.domain.Category;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface CategoryRepository extends CrudRepository<Category, Long> {

    // instead of returning 'null', Java will return the Optional-Type if empty
    Optional<Category> findByDescription(String description);

}
