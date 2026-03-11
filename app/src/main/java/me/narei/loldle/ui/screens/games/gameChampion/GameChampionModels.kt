package me.narei.loldle.ui.screens.games.gameChampion

import kotlinx.serialization.Serializable

@Serializable
data class GameChampionGuess (
    val championId: String,
    val iconUrl: String,
    val fields: List<GameChampionGuessField>
)

@Serializable
enum class GuessStatus {
    CORRECT,
    PARTIAL,
    INCORRECT,
    HIGHER,
    LOWER
}

@Serializable
data class GameChampionGuessField (
    val fieldName: String,
    val fieldValue: String,
    val status: GuessStatus
)
