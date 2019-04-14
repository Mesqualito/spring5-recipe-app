package com.eigenbaumarkt.spring5recipeapp.repositories;

import com.eigenbaumarkt.spring5recipeapp.domain.Category;
import org.springframework.data.repository.CrudRepository;

public interface CategoryRepository extends CrudRepository<Category, Long> {
}
