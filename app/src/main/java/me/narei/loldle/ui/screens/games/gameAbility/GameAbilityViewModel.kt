package me.narei.loldle.ui.screens.games.gameAbility

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import me.narei.loldle.data.AbilityType
import me.narei.loldle.data.ChampionRepository

class GameAbilityViewModel(
    private val championRepository: ChampionRepository
) : ViewModel() {

    private val allChampions = championRepository.getAllChampions()

    private val _state = MutableStateFlow(
        allChampions.random().let { randomChampion ->
            GameAbilityState(
                championToGuess = randomChampion,
                abilityToGuess = randomChampion.abilities.random(),
                guesses = emptyList(),
                availableChampions = allChampions,
                status = GameAbilityStatus.IN_PROGRESS
            )
        }
    )
    val state: StateFlow<GameAbilityState> = _state.asStateFlow()

    fun onAction(action: GameAbilityAction) {
        when (action) {
            is GameAbilityAction.GuessChampion -> handleGuessChampion(action.championId)
            is GameAbilityAction.GuessAbility -> handleGuessAbility(action.abilityType)
            is GameAbilityAction.ResetGame -> handleResetGame()
        }
    }

    private fun handleGuessChampion(championId: String) {
        val currentState = _state.value
        val champion = allChampions.find { it.id == championId } ?: return

        val newGuesses = currentState.guesses + GameAbilityGuess(
            id = championId,
            name = champion.name,
            iconUrl = champion.iconUrl,
            correct = championId == currentState.championToGuess.id
        )

        _state.update {
            it.copy(
                guesses = newGuesses,
                availableChampions = allChampions.filter { champion ->
                    newGuesses.none { guess -> guess.id == champion.id }
                },
                status = if (championId == currentState.championToGuess.id) {
                    GameAbilityStatus.CHAMPION_GUESSED
                } else {
                    GameAbilityStatus.IN_PROGRESS
                }
            )
        }
    }

    private fun handleGuessAbility(abilityType: AbilityType) {
        _state.update {
            it.copy(
                status = if (abilityType == it.abilityToGuess.type) {
                    GameAbilityStatus.WON
                } else {
                    GameAbilityStatus.LOST
                }
            )
        }
    }

    private fun handleResetGame() {
        val randomChampion = allChampions.random()
        _state.update {
            it.copy(
                championToGuess = randomChampion,
                abilityToGuess = randomChampion.abilities.random(),
                guesses = emptyList(),
                availableChampions = allChampions,
                status = GameAbilityStatus.IN_PROGRESS
            )
        }
    }

}