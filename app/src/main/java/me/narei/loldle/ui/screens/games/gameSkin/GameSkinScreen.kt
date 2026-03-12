package me.narei.loldle.ui.screens.games.gameSkin

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.BiasAlignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil3.compose.AsyncImage
import me.narei.loldle.ui.components.shared.DropdownDirection
import me.narei.loldle.ui.components.shared.LazyDropdownMenu
import me.narei.loldle.ui.components.shared.LazyDropdownMenuOption
import me.narei.loldle.ui.screens.games.gameSkin.components.GameSkinGuessCard
import me.narei.loldle.ui.screens.games.gameSkin.components.GameSkinLoseDialog
import me.narei.loldle.ui.screens.games.gameSkin.components.GameSkinWinDialog
import me.narei.loldle.ui.theme.CustomColor
import me.narei.loldle.ui.theme.spacing
import org.koin.compose.viewmodel.koinViewModel
import kotlin.random.Random

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GameSkinScreen(
    viewModel: GameSkinViewModel = koinViewModel(),
    navigateBack: () -> Unit
) {

    val state by viewModel.state.collectAsStateWithLifecycle()

    val randomX = remember(state.skinToGuess.url) { Random.nextFloat() * 2 - 1f }
    val randomY = remember(state.skinToGuess.url) { Random.nextFloat() * 2 - 1f }

    val zoomFactor = 3f
    val windowWidth = 300.dp
    val windowHeight = windowWidth * (9f / 16f)

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Guess skin") },
                navigationIcon = {
                    IconButton(
                        onClick = { navigateBack() }
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

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            contentPadding = PaddingValues(MaterialTheme.spacing.medium),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            item {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = MaterialTheme.spacing.extraLarge),
                    verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.medium),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {

                    Box(
                        modifier = Modifier
                            .size(width = windowWidth, height = windowHeight)
                            .clipToBounds()
                    ) {
                        AsyncImage(
                            model = state.skinToGuess.url,
                            contentDescription = "Skin ${state.skinToGuess.name}",
                            contentScale = ContentScale.FillBounds,
                            modifier = Modifier
                                .requiredSize(
                                    width = windowWidth * zoomFactor,
                                    height = windowHeight * zoomFactor
                                )
                                .align(
                                    BiasAlignment(
                                        horizontalBias = randomX,
                                        verticalBias = randomY
                                    )
                                )
                        )
                    }

                    Text(
                        text = "Guess this skin",
                        style = MaterialTheme.typography.titleLarge
                    )

                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(MaterialTheme.colorScheme.surface)
                            .padding(MaterialTheme.spacing.medium),
                        verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.medium)
                    ) {

                        if (state.status == GameSkinStatus.IN_PROGRESS) {
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
                                onOptionSelect = { championId -> viewModel.onAction(GameSkinAction.GuessChampion(championId)) },
                                direction = DropdownDirection.DOWN
                            )
                        } else {
                            Text(
                                text = "Champion guessed correctly! (${state.championToGuess.name})",
                                style = MaterialTheme.typography.titleMedium,
                                color = CustomColor.Success
                            )

                            Text(
                                text = "Now select skin:",
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.onSurface
                            )

                            LazyDropdownMenu(
                                options = state.championToGuess.skins
                                    .map { skin ->
                                        LazyDropdownMenuOption(
                                            key = skin.name,
                                            title = skin.name
                                        )
                                    },
                                textFieldLabel = "Guess skin",
                                onOptionSelect = { skinName -> viewModel.onAction(GameSkinAction.GuessSkin(skinName)) },
                                direction = DropdownDirection.DOWN
                            )
                        }

                    }
                }

                Spacer(modifier = Modifier.height(MaterialTheme.spacing.large))
            }

            if (state.guesses.isEmpty()) {
                item {
                    Text(
                        text = "No guesses yet.\nSelect first champion below.\nGood luck!",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSecondary,
                        textAlign = TextAlign.Center,
                    )
                }
            } else {
                items(
                    items = state.guesses.reversed(),
                    key = { it.id }
                ) { guess ->
                    GameSkinGuessCard(guess = guess)
                    Spacer(modifier = Modifier.height(MaterialTheme.spacing.small))
                }
            }

        }

    }

    if (state.status == GameSkinStatus.WON) {
        GameSkinWinDialog(
            championToGuess = state.championToGuess,
            skinToGuess = state.skinToGuess,
            navigateBack = navigateBack,
            onAction = { action -> viewModel.onAction(action) }
        )
    } else if (state.status == GameSkinStatus.LOST) {
        GameSkinLoseDialog(
            navigateBack = navigateBack,
            onAction = { action -> viewModel.onAction(action) }
        )
    }

}