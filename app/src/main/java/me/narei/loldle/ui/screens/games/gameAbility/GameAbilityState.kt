package me.narei.loldle.ui.screens.games.gameAbility

import me.narei.loldle.data.Ability
import me.narei.loldle.data.AbilityType
import me.narei.loldle.data.Champion

data class GameAbilityState(
    val championToGuess: Champion,
    val abilityToGuess: Ability,
    val guesses: List<GameAbilityGuess>,
    val status: GameAbilityStatus,
    val availableChampions: List<Champion>
)

sealed interface GameAbilityAction {
    data class GuessChampion(val championId: String) : GameAbilityAction
    data class GuessAbility(val abilityType: AbilityType) : GameAbilityAction
    data object ResetGame : GameAbilityAction
}
