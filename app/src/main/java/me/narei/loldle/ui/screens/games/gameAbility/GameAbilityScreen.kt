package me.narei.loldle.ui.screens.games.gameAbility

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.ColorMatrix
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil3.compose.AsyncImage
import me.narei.loldle.data.AbilityType
import me.narei.loldle.ui.components.shared.AppButton
import me.narei.loldle.ui.components.shared.DropdownDirection
import me.narei.loldle.ui.components.shared.LazyDropdownMenu
import me.narei.loldle.ui.components.shared.LazyDropdownMenuOption
import me.narei.loldle.ui.screens.games.gameAbility.components.GameAbilityGuessCard
import me.narei.loldle.ui.screens.games.gameAbility.components.GameAbilityLoseDialog
import me.narei.loldle.ui.screens.games.gameAbility.components.GameAbilityWinDialog
import me.narei.loldle.ui.theme.CustomColor
import me.narei.loldle.ui.theme.spacing
import org.koin.compose.viewmodel.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GameAbilityScreen(
    viewModel: GameAbilityViewModel = koinViewModel(),
    navigateBack: () -> Unit
) {

    val state by viewModel.state.collectAsStateWithLifecycle()
    val randomRotation = remember { listOf(90f, 180f, 270f).random() }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Guess ability") },
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
                    AsyncImage(
                        model = state.abilityToGuess.url,
                        contentDescription = "Ability ${state.abilityToGuess.name}",
                        modifier = Modifier
                            .size(80.dp)
                            .rotate(randomRotation),
                        colorFilter = ColorFilter.colorMatrix(ColorMatrix().apply { setToSaturation(0f) })
                    )

                    Text(
                        text = "Guess this ability",
                        style = MaterialTheme.typography.titleLarge
                    )

                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(MaterialTheme.colorScheme.surface)
                            .padding(MaterialTheme.spacing.medium),
                        verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.medium)
                    ) {

                       if (state.status == GameAbilityStatus.IN_PROGRESS) {
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
                               onOptionSelect = { championId -> viewModel.onAction(GameAbilityAction.GuessChampion(championId)) },
                               direction = DropdownDirection.DOWN
                           )
                       } else {
                           Text(
                               text = "Champion guessed correctly! (${state.championToGuess.name})",
                               style = MaterialTheme.typography.titleMedium,
                               color = CustomColor.Success
                           )

                           Text(
                               text = "Now select ability type:",
                               style = MaterialTheme.typography.bodyMedium,
                               color = MaterialTheme.colorScheme.onSurface
                           )

                           Row(
                               modifier = Modifier
                                   .fillMaxWidth(),
                               horizontalArrangement = Arrangement.SpaceAround
                           ) {
                               AbilityType.entries.forEach { type ->
                                   AppButton(
                                       modifier = Modifier.width(60.dp).height(60.dp),
                                       title = type.name,
                                       onClick = { viewModel.onAction(GameAbilityAction.GuessAbility(type)) }
                                   )
                               }
                           }
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
                    GameAbilityGuessCard(guess = guess)
                    Spacer(modifier = Modifier.height(MaterialTheme.spacing.small))
                }
            }

        }

    }

    if (state.status == GameAbilityStatus.WON) {
        GameAbilityWinDialog(
            championToGuess = state.championToGuess,
            abilityToGuess = state.abilityToGuess,
            navigateBack = navigateBack,
            onAction = { action -> viewModel.onAction(action) }
        )
    } else if (state.status == GameAbilityStatus.LOST) {
        GameAbilityLoseDialog(
            navigateBack = navigateBack,
            onAction = { action -> viewModel.onAction(action) }
        )
    }

}