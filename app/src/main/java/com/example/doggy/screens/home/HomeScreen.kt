package com.example.doggy.screens.home

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.doggy.network.DogInfo
import com.example.doggy.network.NetworkRepository
import com.example.doggy.network.RetrofitApiBuilder
import com.example.doggy.screens.home.breedlist.BreedListComponent
import com.example.doggy.screens.home.welcome.WelcomeComponent
import kotlinx.coroutines.launch

sealed class HomeUiState {
    object Loading : HomeUiState()
    object Error : HomeUiState()
    data class Success(val dogs: List<DogInfo>) : HomeUiState()
}

class HomeViewModel : ViewModel() {

    private val retrofitAPI = RetrofitApiBuilder().build()
    private val repository = NetworkRepository(retrofitAPI = retrofitAPI)

    var uiState by mutableStateOf<HomeUiState>(HomeUiState.Loading)
        private set

    init {
        viewModelScope.launch {
            fetchDogBreedList()
        }
    }

    private suspend fun fetchDogBreedList() {
        val result = repository.getBreedList()
        result.fold(
            onSuccess = {
                uiState = HomeUiState.Success(result.getOrNull()!!)
            }, onFailure = { e ->
                HomeUiState.Error
                Log.d("On Failure", "Throwable exception on failure: $e")
            }
        )
    }
}

@Composable
fun HomeScreen(
    homeViewModel: HomeViewModel,
    onDogClick: (DogInfo) -> Unit,
    topBarStateChangeCallback: (Boolean) -> Unit
) {
    when (val state: HomeUiState = homeViewModel.uiState) {
        is HomeUiState.Loading -> {
            WelcomeComponent()
            topBarStateChangeCallback(false)
        }
        is HomeUiState.Success -> {
            topBarStateChangeCallback(true)
            BreedListComponent(
                allDog = state.dogs,
                onDogClick = onDogClick,
                jumpToTopButton = true
            )
        }
        HomeUiState.Error -> {
            ErrorComponent()
        }
    }
}
