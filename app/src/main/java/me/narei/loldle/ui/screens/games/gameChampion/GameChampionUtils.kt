package me.narei.loldle.ui.screens.games.gameChampion

fun compareStringField(fieldName: String, guessed: String, target: String): GameChampionGuessField {
    return GameChampionGuessField(fieldName, guessed.lowercase().replaceFirstChar { it.uppercase() }, if (guessed == target) GuessStatus.CORRECT else GuessStatus.INCORRECT)
}

fun compareListField(fieldName: String, guessed: List<String>, target: List<String>): GameChampionGuessField {
    return GameChampionGuessField(fieldName,
        guessed.joinToString("\n") { it.lowercase().replaceFirstChar { char -> char.uppercase() } }, when {
            guessed.containsAll(target) && target.containsAll(guessed) -> GuessStatus.CORRECT
            guessed.intersect(target.toSet()).isNotEmpty() -> GuessStatus.PARTIAL
            else -> GuessStatus.INCORRECT
        })
}

fun compareYearField(fieldName: String, guessed: Int, target: Int): GameChampionGuessField {
    return GameChampionGuessField(fieldName, guessed.toString(), when {
        guessed == target -> GuessStatus.CORRECT
        guessed < target -> GuessStatus.HIGHER
        else -> GuessStatus.LOWER
    })
}
