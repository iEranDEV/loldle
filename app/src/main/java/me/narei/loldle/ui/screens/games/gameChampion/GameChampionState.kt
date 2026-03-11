package me.narei.loldle.ui.screens.games.gameChampion

import me.narei.loldle.data.Champion

data class GameChampionState(
    val championToGuess: Champion,
    val guesses: List<GameChampionGuess>,
    val isGameWon: Boolean,
    val availableChampions: List<Champion>
)

sealed interface GameChampionAction {
    data class GuessChampion(val championId: String) : GameChampionAction
    data object ResetGame : GameChampionAction
}
