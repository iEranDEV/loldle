package me.narei.loldle.ui.screens.championDetail

import androidx.lifecycle.ViewModel
import me.narei.loldle.data.ChampionRepository

class ChampionDetailViewModel(
    championId: String,
    championRepository: ChampionRepository
) : ViewModel() {

    val champion = championRepository.getChampionById(championId)

}
