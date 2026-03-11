package me.narei.loldle.ui.screens

import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.togetherWith
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.navigation3.rememberViewModelStoreNavEntryDecorator
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.runtime.rememberSaveableStateHolderNavEntryDecorator
import androidx.navigation3.ui.NavDisplay
import me.narei.loldle.ui.screens.championDetail.ChampionDetailScreen
import me.narei.loldle.ui.screens.championList.ChampionListScreen
import me.narei.loldle.ui.screens.games.gameAbility.GameAbilityScreen
import me.narei.loldle.ui.screens.games.gameChampion.GameChampionScreen
import me.narei.loldle.ui.screens.home.HomeScreen
import me.narei.loldle.ui.screens.loading.SplashScreen

@Composable
fun NavigationRoot() {

    val backstack = rememberNavBackStack(Screen.Loading)

    NavDisplay(
        backStack = backstack,
        transitionSpec = {
            slideInHorizontally { it } togetherWith slideOutHorizontally { -it }
        },
        popTransitionSpec = {
            slideInHorizontally { -it } togetherWith slideOutHorizontally { it }
        },
        predictivePopTransitionSpec = {
            slideInHorizontally { -it } togetherWith slideOutHorizontally { it }
        },
        entryDecorators = listOf(
            rememberSaveableStateHolderNavEntryDecorator(),
            rememberViewModelStoreNavEntryDecorator()
        ),
        entryProvider = entryProvider {
            entry<Screen.Loading> {
                SplashScreen(
                    navigateToHome = {
                        backstack.clear()
                        backstack.add(Screen.Home)
                    }
                )
            }

            entry<Screen.Home> {
                HomeScreen(
                    navigate = { screen -> backstack.add(screen) }
                )
            }

            entry<Screen.GameChampion> {
                GameChampionScreen(
                    navigateBack = { if (backstack.size > 1) { backstack.removeAt(backstack.size - 1) } }
                )
            }

            entry<Screen.GameAbility> {
                GameAbilityScreen(
                    navigateBack = { if (backstack.size > 1) { backstack.removeAt(backstack.size - 1) } }
                )
            }

            entry<Screen.ChampionList> {
                ChampionListScreen(
                    navigateToDetails = { championId ->
                        backstack.add(Screen.ChampionDetail(championId))
                    },
                    navigateBack = { if (backstack.size > 1) { backstack.removeAt(backstack.size - 1) } }
                )
            }

            entry<Screen.ChampionDetail>(
                metadata = mapOf("extraDataKey" to "extraDataValue")
            ) { key ->
                ChampionDetailScreen(
                    championId = key.championId,
                    navigateBack = { if (backstack.size > 1) { backstack.removeAt(backstack.size - 1) } }
                )
            }
        }
    )
}