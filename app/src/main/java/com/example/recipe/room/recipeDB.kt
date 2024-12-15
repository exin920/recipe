package com.example.recipe.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [recipeentity::class], version = 1, exportSchema = false)
abstract class recipeDB : RoomDatabase() {
    abstract fun RecipeDao(): recipeDAO

    companion object{
        @Volatile
        private var INSTANCE: recipeDB? = null

        fun getDatabase(context: Context): recipeDB{
            return INSTANCE ?: synchronized(this){
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    recipeDB::class.java,
                    "recipe_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }

}