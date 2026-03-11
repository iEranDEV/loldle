package me.narei.loldle.ui.screens.games.gameChampion

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import me.narei.loldle.data.Champion
import me.narei.loldle.data.ChampionRepository

class GameChampionViewModel(
    private val championRepository: ChampionRepository
) : ViewModel() {
    
    private val allChampions = championRepository.getAllChampions()

    private val _state = MutableStateFlow(
        GameChampionState(
            championToGuess = allChampions.random(),
            isGameWon = false,
            availableChampions = allChampions,
            guesses = emptyList()
        )
    )
    val state: StateFlow<GameChampionState> = _state.asStateFlow()

    fun onAction(action: GameChampionAction) {
        when (action) {
            is GameChampionAction.GuessChampion -> handleGuessChampion(action.championId)
            is GameChampionAction.ResetGame -> handleResetGame()
        }
    }

    private fun handleGuessChampion(championId: String) {
        val currentState = _state.value
        val champion = allChampions.find { it.id == championId } ?: return

        val newGuesses = currentState.guesses + GameChampionGuess(
            championId = championId,
            iconUrl = champion.iconUrl,
            fields = listOf(
                compareStringField("gender", champion.gender, currentState.championToGuess.gender),
                compareListField("positions", champion.positions, currentState.championToGuess.positions),
                compareListField("species", champion.species, currentState.championToGuess.species),
                compareStringField("resource", champion.resource, currentState.championToGuess.resource),
                compareListField("regions", champion.regions, currentState.championToGuess.regions),
                compareListField("attackType", champion.attackType, currentState.championToGuess.attackType),
                compareYearField("releaseYear", champion.releaseYear, currentState.championToGuess.releaseYear)
            )
        )

        _state.update {
            it.copy(
                guesses = newGuesses,
                availableChampions = allChampions.filter { c ->
                    newGuesses.none { c2 -> c2.championId == c.id }
                },
                isGameWon = championId == currentState.championToGuess.id
            )
        }
    }

    private fun handleResetGame() {
        _state.update {
            it.copy(
                championToGuess = allChampions.random(),
                guesses = emptyList(),
                isGameWon = false,
                availableChampions = allChampions
            )
        }
    }

}
