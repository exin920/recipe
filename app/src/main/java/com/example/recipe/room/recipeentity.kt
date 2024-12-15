package com.example.recipe.room

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "recipe")
data class recipeentity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val name: String,
    val description: String,
    val ingredients: String,
    val steps: String,
    val category: String,
    val imageUri: String
)
