package me.narei.loldle.ui.screens.games.gameChampion

import androidx.lifecycle.ViewModel
import me.narei.loldle.data.ChampionRepository

class GameChampionViewModel(
    private val championRepository: ChampionRepository
) : ViewModel() {
    
    val champions = championRepository.getAllChampions()

}
