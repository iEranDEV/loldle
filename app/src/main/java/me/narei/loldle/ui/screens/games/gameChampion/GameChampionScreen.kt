package me.narei.loldle.ui.screens.games.gameChampion

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.DialogProperties
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil3.compose.AsyncImage
import me.narei.loldle.ui.components.games.gameChampion.ChampionGuessRow
import me.narei.loldle.ui.components.shared.LazyDropdownMenu
import me.narei.loldle.ui.components.shared.LazyDropdownMenuOption
import me.narei.loldle.ui.theme.spacing
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GameChampionScreen(
    modifier: Modifier = Modifier,
    viewModel: GameChampionViewModel = koinViewModel(),
    backToHome: () -> Unit
) {

    val focusManager = LocalFocusManager.current

    val champions = viewModel.champions
    val guesses by viewModel.guesses.collectAsStateWithLifecycle()
    val isGameWon by viewModel.isGameWon.collectAsStateWithLifecycle()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Guess champion") },
                navigationIcon = {
                    IconButton(
                        onClick = {
                            focusManager.clearFocus()
                            backToHome()
                        }
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Rounded.ArrowBack,
                            contentDescription = "Back to home"
                        )
                    }
                }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = modifier
                .padding(innerPadding),
            verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.large)
        ) {

            Column(
                modifier = Modifier.padding(MaterialTheme.spacing.medium)
            ) {
                Text(viewModel.championToGuess.name)

                LazyDropdownMenu(
                    options = champions.map { champion ->
                        LazyDropdownMenuOption(
                            key = champion.id,
                            title = champion.name,
                            icon = {
                                AsyncImage(
                                    model = champion.iconUrl,
                                    contentDescription = "Icon ${champion.name}",
                                    modifier = Modifier
                                        .size(50.dp)
                                )
                            }
                        )
                    },
                    onOptionSelect = { championId -> viewModel.guessChampion(championId) }
                )
            }

            LazyColumn() {
                items(
                    items = guesses.reversed(),
                    key = { it.championId }
                ) { guess ->
                    ChampionGuessRow( guess = guess )
                }
            }

        }
    }

    if (isGameWon) {
        AlertDialog(
            onDismissRequest = {},
            properties = DialogProperties(
                dismissOnBackPress = false,
                dismissOnClickOutside = false
            ),
            title = {
                Text(text = "Zwycięstwo!")
            },
            text = {
                Text(text = "Udało Ci się odgadnąć postać (${viewModel.championToGuess.name}). Co chcesz teraz zrobić?")
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        viewModel.resetGame()
                    }
                ) {
                    Text("AGAIN")
                }
            },
            dismissButton = {
                TextButton(
                    onClick = {
                        focusManager.clearFocus()
                        backToHome()
                    }
                ) {
                    Text("POWRÓT")
                }
            }
        )
    }

}