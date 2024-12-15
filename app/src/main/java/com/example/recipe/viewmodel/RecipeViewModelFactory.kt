package com.example.recipe.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.recipe.repository.RecipeRepository
import com.example.recipe.room.recipeDAO

class RecipeViewModelFactory(private val recipeDao: recipeDAO) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(RecipeViewModel::class.java)) {
            return RecipeViewModel(RecipeRepository(recipeDao)) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}