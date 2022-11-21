package com.example.doggy.screens.searchbar

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.focusable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Clear
import androidx.compose.material.icons.rounded.Search
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.doggy.network.DogInfo
import com.example.doggy.network.NetworkRepository
import com.example.doggy.network.RetrofitApiBuilder
import com.example.doggy.screens.home.breedlist.BreedListComponent
import com.example.doggy.ui.theme.myColour2
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SearchViewModel : ViewModel() {
    var uiState by mutableStateOf(SearchScreenState())
        private set

    private val retrofitAPI = RetrofitApiBuilder().build()
    private val repository = NetworkRepository(retrofitAPI = retrofitAPI)

    init {
        viewModelScope.launch {
            Log.d("Thread", "View Model Scope: ${(Thread.currentThread().name)}")
            delay(10_000L)
            Log.d("Thread", "View Model Scope: 2")
        }

        GlobalScope.launch {
            Log.d("Thread", "Global Scope: ${(Thread.currentThread().name)}")
            delay(10_000L)
            Log.d("Thread", "Global Scope: 2")
        }
    }

    override fun onCleared() {
        super.onCleared()
        Log.d("Thread", "view model cleared")
    }

    fun search(query: String) {
        repository.searchBreedByName(name = query) { searchedBreedList ->
            uiState = SearchScreenState(
                inputText = query,
                dogs = searchedBreedList
            )
        }
    }
}

data class SearchScreenState(
    val inputText: String = "",
    val dogs: List<DogInfo> = emptyList()
)

@Composable
fun SearchScreen(
    searchViewModel: SearchViewModel,
    onDogClick: (DogInfo) -> Unit
) {
    Column {
        SearchBar(onInputValueChange = { textInput ->
            Log.d("SearchBar", "text change: $textInput")
            searchViewModel.search(textInput)
        })

        Divider(
            color = Color.Transparent,
            thickness = 3.dp,
            modifier = Modifier.padding(vertical = 1.dp)
        )

        if (searchViewModel.uiState.dogs.isNotEmpty()) {
            BreedListComponent(
                allDog = searchViewModel.uiState.dogs,
                onDogClick = onDogClick,
                jumpToTopButton = false
            )
        }
    }
}

@Composable
fun SearchBar(
    onInputValueChange: (String) -> Unit,
) {
    var textState by remember { mutableStateOf("") }

    OutlinedTextField(
        value = textState,
        onValueChange = {
            textState = it
            onInputValueChange(it)
        },
        leadingIcon = {
            Icon(
                imageVector = Icons.Rounded.Search,
                contentDescription = "",
                tint = myColour2.copy(
                    alpha = ContentAlpha.medium
                )
            )
        },
        placeholder = {
            Text(
                text = "Search Dogs...",
                fontStyle = FontStyle.Italic
            )
        },
        trailingIcon = {
            Icon(
                imageVector = Icons.Rounded.Clear,
                contentDescription = "clear text",
                modifier = Modifier
                    .clickable {
                        textState = ""
                    },
                tint = myColour2
            )
        },
        modifier = Modifier.fillMaxWidth()
            .focusable(),
        colors = TextFieldDefaults.outlinedTextFieldColors(
            unfocusedBorderColor = Color.White.copy(
                alpha = ContentAlpha.medium
            ),
            focusedBorderColor = myColour2,
            cursorColor = Color.White,
        ),
        singleLine = true,
        maxLines = 1,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
        shape = RectangleShape
    )
}
