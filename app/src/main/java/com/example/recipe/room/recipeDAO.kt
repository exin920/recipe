package com.example.recipe.room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update


@Dao
interface recipeDAO {

    @Insert
    suspend fun insertrecipeentity(RecipeEntity:recipeentity)

    @Update
    suspend fun updaterecipeentity(RecipeEntity:recipeentity)

    @Delete
    suspend fun deleterecipeentity(RecipeEntity:recipeentity)

    @Query("SELECT * FROM recipe")
    suspend fun getAllRecipes():List<recipeentity>

    @Query("SELECT * FROM recipe WHERE id = :recipeId")
    suspend fun getRecipeById(recipeId:Int): recipeentity?

    @Query(
        """
    SELECT * FROM recipe
    WHERE (:search = '' OR name LIKE '%' || :search || '%' OR description LIKE '%' || :search || '%')
    AND (:category = 'All' OR category = :category OR category LIKE '%' || :search || '%')
    """
    )
    suspend fun searchRecipes(search: String, category: String): List<recipeentity>

}