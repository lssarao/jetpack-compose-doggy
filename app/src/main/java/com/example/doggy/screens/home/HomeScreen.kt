package com.example.doggy.screens.home

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.doggy.network.DogInfo
import com.example.doggy.network.RetrofitApiBuilder
import com.example.doggy.network.NetworkRepository
import com.example.doggy.screens.home.breedlist.BreedListComponent
import com.example.doggy.screens.home.welcome.WelcomeComponent

sealed class HomeUiState {
    object Loading : HomeUiState()
    data class Success(val dogs: List<DogInfo>) : HomeUiState()
}

class HomeViewModel : ViewModel() {

    private val retrofitAPI = RetrofitApiBuilder().build()
    private val repository = NetworkRepository(retrofitAPI = retrofitAPI)

    var uiState by mutableStateOf<HomeUiState>(HomeUiState.Loading)
        private set

    init {
        fetchDogBreedList()
    }

    private fun fetchDogBreedList() {
        repository.getBreedList { dogBreedList ->
            uiState = HomeUiState.Success(dogBreedList)
        }
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
    }
}
