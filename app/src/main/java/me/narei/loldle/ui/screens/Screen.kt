package me.narei.loldle.ui.screens

import androidx.navigation3.runtime.NavKey
import kotlinx.serialization.Serializable

@Serializable
sealed interface Screen : NavKey {

    @Serializable
    data object Loading : Screen

    @Serializable
    data object Home : Screen

    @Serializable
    data object GameChampion : Screen

    @Serializable
    data object GameAbility : Screen

    @Serializable
    data object GameSkin : Screen

    @Serializable
    data object ChampionList : Screen

    @Serializable
    data class ChampionDetail(val championId: String) : Screen

}