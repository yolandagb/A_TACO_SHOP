package com.lm2a.data;

import org.springframework.data.repository.CrudRepository;

import com.lm2a.model.Ingredient;

public interface IngredientRepository extends CrudRepository<Ingredient, String>{
}
