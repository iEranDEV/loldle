package me.narei.loldle.ui.screens.splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import me.narei.loldle.data.ChampionRepository

class SplashScreenViewModel(
    private val championRepository: ChampionRepository
) : ViewModel() {

    val loadingState = championRepository.loadingState

    init {
        loadData()
    }

    private fun loadData() {
        viewModelScope.launch {
            championRepository.loadChampionsData()
        }
    }

}