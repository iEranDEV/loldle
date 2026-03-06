package me.narei.loldle.ui.screens.splash

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.sp
import me.narei.loldle.data.LoadingState
import me.narei.loldle.ui.theme.spacing
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

    Scaffold() { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(MaterialTheme.spacing.large),
            verticalArrangement = Arrangement.SpaceEvenly,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Loldle",
                    style = MaterialTheme.typography.titleLarge,
                    fontSize = 50.sp,
                    color = MaterialTheme.colorScheme.primary
                )
                Text(
                    text = "by Narei",
                    fontSize = 12.sp
                )
            }

            when (state) {
                is LoadingState.Idle -> {  }

                is LoadingState.Loading -> {
                    val progress = (state as LoadingState.Loading).progress
                    Text("Loading: ${(progress * 100).toInt()}%")
                }

                is LoadingState.Success -> {
                    Text("Success!")
                }

                is LoadingState.Error -> {
                    Text("Error: ${(state as LoadingState.Error).message}")
                }
            }
        }
    }

}