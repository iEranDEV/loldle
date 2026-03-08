package me.narei.loldle.ui.screens.games.gameChampion

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.DialogProperties
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil3.compose.AsyncImage
import me.narei.loldle.ui.components.games.gameChampion.ChampionGuessRow
import me.narei.loldle.ui.components.shared.DropdownDirection
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

    val horizontalScrollState = rememberScrollState()
    val guessesListState = rememberLazyListState()

    LaunchedEffect(guesses.size) {
        if (guesses.isNotEmpty()) {
            guessesListState.animateScrollToItem(0)
        }
    }

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
                .fillMaxSize()
                .padding(innerPadding)
                .imePadding(),
            verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.large)
        ) {

            LazyColumn(
                state = guessesListState,
                modifier = Modifier
                    .weight(1f)
                    .horizontalScroll(horizontalScrollState)
                    .padding(vertical = MaterialTheme.spacing.medium),
                verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.small)
            ) {
                items(
                    items = guesses.reversed(),
                    key = { it.championId }
                ) { guess ->
                    ChampionGuessRow( guess = guess )
                }
            }

            Column(
                modifier = Modifier.padding(MaterialTheme.spacing.medium)
            ) {
                Text(viewModel.championToGuess.name)

                LazyDropdownMenu(
                    options = champions
                        .filter { !guesses.any { guess -> guess.championId == it.id } }
                        .map { champion ->
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
                    onOptionSelect = { championId -> viewModel.guessChampion(championId) },
                    direction = DropdownDirection.UP
                )
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