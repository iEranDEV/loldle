package me.narei.loldle.ui.screens.championList

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import me.narei.loldle.data.Champion
import me.narei.loldle.data.ChampionRepository

class ChampionListViewModel(
    private val championRepository: ChampionRepository
) : ViewModel() {

    private val _champions = MutableStateFlow(championRepository.getAllChampions())
    val champions: StateFlow<List<Champion>> = _champions.asStateFlow()

}