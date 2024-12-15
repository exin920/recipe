package com.example.recipe.repository

import android.icu.text.StringSearch
import com.example.recipe.room.recipeDAO
import com.example.recipe.room.recipeentity

class RecipeRepository(private val RecipeDao:recipeDAO) {

    suspend fun getAllRecipes(): List<recipeentity>{
        return RecipeDao.getAllRecipes()
    }

    suspend fun searchRecipes(search: String,category: String): List<recipeentity>{
        return RecipeDao.searchRecipes(search,category)
    }
}