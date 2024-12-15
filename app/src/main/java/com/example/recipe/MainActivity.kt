package com.example.recipe

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.recipe.room.recipeDAO
import com.example.recipe.room.recipeDB
import com.example.recipe.room.recipeentity
import com.example.recipe.ui.theme.RecipeTheme
import com.example.recipe.viewmodel.RecipeViewModel
import com.example.recipe.viewmodel.RecipeViewModelFactory

class MainActivity : ComponentActivity() {
    private val viewModel: RecipeViewModel by viewModels {
        val recipeDao = recipeDB.getDatabase(this).RecipeDao() // 確保這裡初始化正確
        RecipeViewModelFactory(recipeDao)  // 傳遞給工廠
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            RecipeApp(viewModel = viewModel)
        }
    }
}

@Composable
fun RecipeApp(viewModel: RecipeViewModel) {
    // 狀態管理
    var searchQuery by remember { mutableStateOf("") }
    var selectedCategory by remember { mutableStateOf("All") }

    // 從 ViewModel 獲取搜尋結果
    val recipes by viewModel.recipes.collectAsState(initial = emptyList())

    Column(modifier = Modifier.fillMaxSize()) {
        // 搜尋欄元件
        SearchBar(
            searchQuery = searchQuery,
            onSearchQueryChanged = { searchQuery = it },
            selectedCategory = selectedCategory,
            onCategorySelected = { selectedCategory = it },
            onSearchClicked = {
                viewModel.searchRecipes(searchQuery, selectedCategory)
            }
        )

        // 顯示搜尋結果的列表
        if (recipes.isNotEmpty()) {
            LazyColumn(modifier = Modifier.fillMaxSize().padding(16.dp)) {
                items(recipes) { recipe ->
                    RecipeCard(recipe = recipe)
                }
            }
        } else {
            // 無搜尋結果的提示
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = androidx.compose.ui.Alignment.Center) {
                Text("No recipes found. Try searching with different keywords!")
            }
        }
    }
}
@Composable
fun SearchBar(
    searchQuery: String,
    onSearchQueryChanged: (String) -> Unit,
    selectedCategory: String,
    onCategorySelected: (String) -> Unit,
    onSearchClicked: () -> Unit
) {
    Column(modifier = Modifier.fillMaxWidth().padding(16.dp)) {
        // 搜尋輸入框
        TextField(
            value = searchQuery,
            onValueChange = onSearchQueryChanged,
            label = { Text("Search Recipes") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        Row(modifier = Modifier.fillMaxWidth()) {
            // 下拉選單：選擇分類
            DropdownMenu(
                items = listOf("All", "Dessert", "Main Course", "Appetizer"),
                selectedItem = selectedCategory,
                onItemSelected = onCategorySelected
            )
            Spacer(modifier = Modifier.width(8.dp))
            Button(onClick = onSearchClicked, modifier = Modifier.weight(1f)) {
                Text("Search")
            }
        }
    }
}

@Composable
fun RecipeCard(recipe: recipeentity) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        elevation = 4.dp
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = recipe.name, style = MaterialTheme.typography.h6)
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = recipe.description, style = MaterialTheme.typography.body2)
        }
    }
}

// 簡易下拉選單元件
@Composable
fun DropdownMenu(
    items: List<String>,
    selectedItem: String,
    onItemSelected: (String) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }
    Box {
        Button(onClick = { expanded = true }) {
            Text(text = selectedItem)
        }
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            items.forEach { item ->
                DropdownMenuItem(onClick = {
                    expanded = false
                    onItemSelected(item)
                }) {
                    Text(text = item)
                }
            }
        }
    }
}