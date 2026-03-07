package me.narei.loldle.ui.screens.games.gameChampion

import kotlinx.serialization.Serializable

@Serializable
data class GameChampionGuess (
    val championId: String,
    val iconUrl: String,
    val fields: List<GameChampionGuessField>
)

@Serializable
enum class CorrectStatus {
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
    val status: CorrectStatus
)

fun compareStringField(fieldName: String, guessed: String, target: String): GameChampionGuessField {
    return GameChampionGuessField(fieldName, guessed, if (guessed == target) CorrectStatus.CORRECT else CorrectStatus.INCORRECT)
}

fun compareListField(fieldName: String, guessed: List<Any>, target: List<Any>): GameChampionGuessField {
    return GameChampionGuessField(fieldName, guessed.joinToString(", "), when {
        guessed.containsAll(target) && target.containsAll(guessed) -> CorrectStatus.CORRECT
        guessed.intersect(target.toSet()).isNotEmpty() -> CorrectStatus.PARTIAL
        else -> CorrectStatus.INCORRECT
    })
}

fun compareYearField(fieldName: String, guessed: Int, target: Int): GameChampionGuessField {
    return GameChampionGuessField(fieldName, guessed.toString(), when {
        guessed == target -> CorrectStatus.CORRECT
        guessed < target -> CorrectStatus.HIGHER
        else -> CorrectStatus.LOWER
    })
}
