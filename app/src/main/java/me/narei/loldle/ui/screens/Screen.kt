package me.narei.loldle.ui.screens

import androidx.navigation3.runtime.NavKey
import kotlinx.serialization.Serializable

@Serializable
sealed interface Screen : NavKey {

    @Serializable
    data object Splash : Screen

    @Serializable
    data object Home : Screen

    @Serializable
    data object GameChampion : Screen

    @Serializable
    data object GameSpell : Screen

    @Serializable
    data object GameSkin : Screen

}