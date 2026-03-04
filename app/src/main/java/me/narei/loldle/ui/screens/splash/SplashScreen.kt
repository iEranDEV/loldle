package me.narei.loldle.ui.screens.splash

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import me.narei.loldle.data.LoadingState
import org.koin.androidx.compose.koinViewModel

@Composable
fun SplashScreen(
    viewModel: SplashViewModel = koinViewModel(),
    navigateToHome: () -> Unit
) {

    val state by viewModel.loadingState.collectAsState()

    LaunchedEffect(state) {
        if (state is LoadingState.Success) {
            navigateToHome()
        }
    }

    when (state) {
        is LoadingState.Idle -> {  }

        is LoadingState.Loading -> {
            val progress = (state as LoadingState.Loading).progress
            Text("Ładowanie: ${(progress * 100).toInt()}%")
        }

        is LoadingState.Success -> {
            Text("Gotowe!")
        }

        is LoadingState.Error -> {
            Text("Błąd: ${(state as LoadingState.Error).message}")
        }
    }

}