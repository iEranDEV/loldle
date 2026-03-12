package me.narei.loldle.ui.screens.games.gameSkin

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import me.narei.loldle.data.ChampionRepository

class GameSkinViewModel(
    private val championRepository: ChampionRepository
) : ViewModel() {

    private val allChampions = championRepository.getAllChampions()

    private val _state = MutableStateFlow(
        allChampions.random().let { randomChampion ->
            GameSkinState(
                championToGuess = randomChampion,
                skinToGuess = randomChampion.skins.random(),
                guesses = emptyList(),
                availableChampions = allChampions,
                status = GameSkinStatus.IN_PROGRESS
            )
        }
    )
    val state: StateFlow<GameSkinState> = _state.asStateFlow()

    fun onAction(action: GameSkinAction) {
        when (action) {
            is GameSkinAction.GuessChampion -> handleGuessChampion(action.championId)
            is GameSkinAction.GuessSkin -> handleGuessSkin(action.skinName)
            is GameSkinAction.ResetGame -> handleResetGame()
        }
    }

    private fun handleGuessChampion(championId: String) {
        val currentState = _state.value
        val champion = allChampions.find { it.id == championId } ?: return

        val newGuesses = currentState.guesses + GameSkinGuess(
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
                    GameSkinStatus.CHAMPION_GUESSED
                } else {
                    GameSkinStatus.IN_PROGRESS
                }
            )
        }
    }

    private fun handleGuessSkin(skinName: String) {
        _state.update {
            it.copy(
                status = if (skinName == it.skinToGuess.name) {
                    GameSkinStatus.WON
                } else {
                    GameSkinStatus.LOST
                }
            )
        }
    }

    private fun handleResetGame() {
        val randomChampion = allChampions.random()
        _state.update {
            it.copy(
                championToGuess = randomChampion,
                skinToGuess = randomChampion.skins.random(),
                guesses = emptyList(),
                availableChampions = allChampions,
                status = GameSkinStatus.IN_PROGRESS
            )
        }
    }

}