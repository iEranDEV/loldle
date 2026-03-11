package me.narei.loldle.ui.screens.loading

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import me.narei.loldle.data.ChampionRepository

class LoadingViewModel(
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