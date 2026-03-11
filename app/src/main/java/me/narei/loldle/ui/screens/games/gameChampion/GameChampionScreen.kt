package me.narei.loldle.ui.screens.games.gameChampion

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material.icons.rounded.Home
import androidx.compose.material.icons.rounded.Replay
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil3.compose.AsyncImage
import me.narei.loldle.ui.screens.games.gameChampion.components.ChampionGuessRow
import me.narei.loldle.ui.components.shared.AppButton
import me.narei.loldle.ui.components.shared.DropdownDirection
import me.narei.loldle.ui.components.shared.LazyDropdownMenu
import me.narei.loldle.ui.components.shared.LazyDropdownMenuOption
import me.narei.loldle.ui.theme.CustomColor
import me.narei.loldle.ui.theme.spacing
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GameChampionScreen(
    modifier: Modifier = Modifier,
    viewModel: GameChampionViewModel = koinViewModel(),
    navigateBack: () -> Unit
) {

    val focusManager = LocalFocusManager.current

    val state by viewModel.state.collectAsStateWithLifecycle()

    val horizontalScrollState = rememberScrollState()
    val guessesListState = rememberLazyListState()

    LaunchedEffect(state.guesses.size) {
        if (state.guesses.isNotEmpty()) {
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
                            navigateBack()
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
                .imePadding()
        ) {

            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
            ) {
                if (state.guesses.isEmpty()) {
                    Text(
                        text = "No guesses yet.\nSelect first champion below.\nGood luck!",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSecondary,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.align(Alignment.Center)
                    )
                } else {
                    LazyColumn(
                        state = guessesListState,
                        modifier = Modifier
                            .fillMaxSize()
                            .horizontalScroll(horizontalScrollState)
                            .padding(vertical = MaterialTheme.spacing.medium),
                        verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.small)
                    ) {
                        items(
                            items = state.guesses.reversed(),
                            key = { it.championId }
                        ) { guess ->
                            ChampionGuessRow(guess = guess)
                        }
                    }
                }
            }

            Column(
                modifier = Modifier.padding(MaterialTheme.spacing.medium)
            ) {

                LazyDropdownMenu(
                    options = state.availableChampions
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
                    textFieldLabel = "Guess champion",
                    onOptionSelect = { championId -> viewModel.onAction(GameChampionAction.GuessChampion(championId)) },
                    direction = DropdownDirection.UP
                )
            }

        }
    }

    if (state.isGameWon) {
        Dialog (
            onDismissRequest = {},
            properties = DialogProperties(
                dismissOnBackPress = false,
                dismissOnClickOutside = false
            )
        ) {
            Surface(
                color = MaterialTheme.colorScheme.background,
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier.padding(MaterialTheme.spacing.medium),
                    verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.medium)
                ) {

                    Text(
                        text = "You win!",
                        style = MaterialTheme.typography.titleLarge,
                        color = CustomColor.Success
                    )

                    Row(
                        modifier = modifier
                            .fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.large)
                    ) {
                        AsyncImage(
                            model = state.championToGuess.iconUrl,
                            contentDescription = "Icon ${state.championToGuess.name}",
                            modifier = Modifier
                                .size(70.dp)
                        )

                        Column {
                            Text(
                                text = state.championToGuess.name,
                                style = MaterialTheme.typography.titleLarge
                            )

                            Text(
                                text = state.championToGuess.title,
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onSecondary
                            )
                        }
                    }

                    Text(
                        text = "You successfully guessed champion (${state.championToGuess.name}). What do you want to do next?",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurface
                    )

                    Column(
                        modifier = Modifier
                            .fillMaxWidth(),
                        verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.medium),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {

                        AppButton(
                            onClick = {
                                focusManager.clearFocus()
                                navigateBack()
                            },
                            title = "Back to Menu",
                            icon = Icons.Rounded.Home
                        )

                        AppButton(
                            onClick = { viewModel.onAction(GameChampionAction.ResetGame) },
                            title = "Play Again",
                            icon = Icons.Rounded.Replay
                        )

                    }

                }
            }
        }
    }

}