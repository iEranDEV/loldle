package me.narei.loldle.ui.screens.games.gameAbility.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Home
import androidx.compose.material.icons.rounded.Replay
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import me.narei.loldle.ui.components.shared.AppButton
import me.narei.loldle.ui.screens.games.gameAbility.GameAbilityAction
import me.narei.loldle.ui.theme.CustomColor
import me.narei.loldle.ui.theme.spacing

@Composable
fun GameAbilityLoseDialog(
    modifier: Modifier = Modifier,
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
                    text = "You lose!",
                    style = MaterialTheme.typography.titleLarge,
                    color = CustomColor.Error
                )

                Row(
                    modifier = modifier
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.large)
                ) {

                    Text(
                        text = "You lost! Unfortunately you guessed wrong ability type.\nWhat do you want to do next?",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurface
                    )

                }

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