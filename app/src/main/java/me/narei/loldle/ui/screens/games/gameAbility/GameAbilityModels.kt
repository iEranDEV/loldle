package me.narei.loldle.ui.screens.games.gameAbility

enum class GameAbilityStatus {
    IN_PROGRESS,
    CHAMPION_GUESSED,
    WON,
    LOST
}

data class GameAbilityGuess(
    val id: String,
    val name: String,
    val iconUrl: String,
    val correct: Boolean
)