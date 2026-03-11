package me.narei.loldle.ui.screens.championList

import me.narei.loldle.data.Champion

data class ChampionListState(
    val champions: List<Champion>,
    val searchQuery: String = "",
    val isSearchActive: Boolean = false
)

sealed interface ChampionListAction {
    data object ToggleSearch : ChampionListAction
    data class UpdateSearchQuery(val query: String) : ChampionListAction
}