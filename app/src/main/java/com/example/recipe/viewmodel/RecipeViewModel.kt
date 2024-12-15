package com.example.recipe.viewmodel

import android.icu.text.StringSearch
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.recipe.repository.RecipeRepository
import com.example.recipe.room.recipeentity
import kotlinx.coroutines.launch

class RecipeViewModel(private val recipeRepository: RecipeRepository) : ViewModel() {

    private val  _recipes = MutableLiveData<List<recipeentity>>()
    val recipes: LiveData<List<recipeentity>> = _recipes

    fun getAllRecipes(){
        viewModelScope.launch {
            val recipelist = recipeRepository.getAllRecipes()
            _recipes.value = recipelist
        }
    }

    fun searchRecipes(search: String,category: String){
        viewModelScope.launch {
            val filteredlist = recipeRepository.searchRecipes(search,category)
            _recipes.value = filteredlist
        }
    }

    init{
        getAllRecipes()
    }
}