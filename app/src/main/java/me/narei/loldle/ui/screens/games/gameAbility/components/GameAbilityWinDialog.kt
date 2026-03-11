package me.narei.loldle.ui.screens.games.gameAbility.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Home
import androidx.compose.material.icons.rounded.Replay
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import coil3.compose.AsyncImage
import me.narei.loldle.data.Ability
import me.narei.loldle.data.Champion
import me.narei.loldle.ui.components.shared.AppButton
import me.narei.loldle.ui.screens.games.gameAbility.GameAbilityAction
import me.narei.loldle.ui.theme.CustomColor
import me.narei.loldle.ui.theme.spacing

@Composable
fun GameAbilityWinDialog(
    modifier: Modifier = Modifier,
    championToGuess: Champion,
    abilityToGuess: Ability,
    navigateBack: () -> Unit,
    onAction: (GameAbilityAction) -> Unit
) {

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
                    modifier = modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.medium)
                ) {
                    AsyncImage(
                        model = championToGuess.iconUrl,
                        contentDescription = "Icon ${championToGuess.name}",
                        modifier = Modifier
                            .size(70.dp)
                    )

                    Column {
                        Text(
                            text = championToGuess.name,
                            style = MaterialTheme.typography.titleLarge
                        )

                        Text(
                            text = championToGuess.title,
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSecondary
                        )
                    }
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.medium)
                ) {

                    Text(
                        modifier = Modifier.width(40.dp),
                        text = abilityToGuess.type.name,
                        color = MaterialTheme.colorScheme.primary,
                        style = MaterialTheme.typography.titleLarge
                    )

                    AsyncImage(
                        model = abilityToGuess.url,
                        contentDescription = "Ability ${abilityToGuess.name}",
                        modifier = Modifier.size(40.dp)
                    )

                    Text(abilityToGuess.name)

                }

                Text(
                    text = "You successfully guessed champion (${championToGuess.name}) and ability ${abilityToGuess.type.name} - ${abilityToGuess.name}.\nWhat do you want to do next?",
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
                        onClick = { navigateBack() },
                        title = "Back to Menu",
                        icon = Icons.Rounded.Home
                    )

                    AppButton(
                        onClick = { onAction(GameAbilityAction.ResetGame) },
                        title = "Play Again",
                        icon = Icons.Rounded.Replay
                    )

                }

            }
        }
    }

}