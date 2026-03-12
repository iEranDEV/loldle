package me.narei.loldle.ui.screens.games.gameSkin

enum class GameSkinStatus {
    IN_PROGRESS,
    CHAMPION_GUESSED,
    WON,
    LOST
}

data class GameSkinGuess(
    val id: String,
    val name: String,
    val iconUrl: String,
    val correct: Boolean
)