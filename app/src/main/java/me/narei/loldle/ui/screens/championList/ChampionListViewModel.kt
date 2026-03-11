package me.narei.loldle.ui.screens.championList

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import me.narei.loldle.data.ChampionRepository

class ChampionListViewModel(
    private val championRepository: ChampionRepository
) : ViewModel() {

    private val allChampions = championRepository.getAllChampions()

    private val _state = MutableStateFlow(
        ChampionListState(
            champions = allChampions
        )
    )
    val state: StateFlow<ChampionListState> = _state.asStateFlow()

    fun onAction(action: ChampionListAction) {
        when (action) {
            is ChampionListAction.ToggleSearch -> handleToggleSearch()
            is ChampionListAction.UpdateSearchQuery -> handleUpdateSearchQuery(action.query)
        }
    }

    private fun handleToggleSearch() {
        _state.update { currentState ->
            val willBeActive = !currentState.isSearchActive
            currentState.copy(
                isSearchActive = willBeActive,
                searchQuery = if (!willBeActive) "" else currentState.searchQuery,
                champions = if (!willBeActive) allChampions else currentState.champions
            )
        }
    }

    private fun handleUpdateSearchQuery(query: String) {
        _state.update { currentState ->
            val filteredList = if (query.isBlank()) {
                allChampions
            } else {
                allChampions.filter { it.name.contains(query, ignoreCase = true) }
            }

            currentState.copy(
                searchQuery = query,
                champions = filteredList
            )
        }
    }

}