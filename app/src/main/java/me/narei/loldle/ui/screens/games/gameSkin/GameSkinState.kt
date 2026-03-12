package me.narei.loldle.ui.screens.games.gameSkin

import me.narei.loldle.data.Champion
import me.narei.loldle.data.Skin

data class GameSkinState(
    val championToGuess: Champion,
    val skinToGuess: Skin,
    val guesses: List<GameSkinGuess>,
    val status: GameSkinStatus,
    val availableChampions: List<Champion>
)

sealed interface GameSkinAction {
    data class GuessChampion(val championId: String) : GameSkinAction
    data class GuessSkin(val skinName: String) : GameSkinAction
    data object ResetGame : GameSkinAction
}
