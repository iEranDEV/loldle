package me.narei.loldle.ui.screens

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.navigation3.rememberViewModelStoreNavEntryDecorator
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.runtime.rememberSaveableStateHolderNavEntryDecorator
import androidx.navigation3.ui.NavDisplay
import me.narei.loldle.ui.screens.championList.ChampionListScreen
import me.narei.loldle.ui.screens.home.HomeScreen
import me.narei.loldle.ui.screens.splash.SplashScreen

@Composable
fun NavigationRoot() {

    val backstack = rememberNavBackStack(Screen.Splash)

    NavDisplay(
        backStack = backstack,
        entryDecorators = listOf(
            rememberSaveableStateHolderNavEntryDecorator(),
            rememberViewModelStoreNavEntryDecorator()
        ),
        entryProvider = entryProvider {
            entry<Screen.Splash> {
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

            entry<Screen.ChampionList> {
                ChampionListScreen(
                    navigateToDetails = { championId ->
                        // TODO: Navigate to champion details screen
                    },
                    backToHome = { if (backstack.size > 1) { backstack.removeAt(backstack.size - 1) } }
                )
            }
        }
    )
}