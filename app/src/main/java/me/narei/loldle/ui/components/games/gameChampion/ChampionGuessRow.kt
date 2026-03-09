package me.narei.loldle.ui.components.games.gameChampion

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowDownward
import androidx.compose.material.icons.rounded.ArrowUpward
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import me.narei.loldle.ui.screens.games.gameChampion.CorrectStatus
import me.narei.loldle.ui.screens.games.gameChampion.GameChampionGuess
import me.narei.loldle.ui.theme.CustomColor
import me.narei.loldle.ui.theme.spacing

@Composable
fun ChampionGuessRow(
    modifier: Modifier = Modifier,
    guess: GameChampionGuess
) {

    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = MaterialTheme.spacing.medium),
        horizontalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.small)
    ) {

        AsyncImage(
            model = guess.iconUrl,
            contentDescription = "Icon ${guess.championId}",
            modifier = Modifier
                .size(70.dp)
        )

        guess.fields.forEach { field ->

            Box(
                modifier = Modifier
                    .background(when (field.status) {
                        CorrectStatus.CORRECT -> CustomColor.Success
                        CorrectStatus.PARTIAL -> CustomColor.Warning
                        else -> CustomColor.Error
                    })
                    .border(1.dp, when (field.status) {
                        CorrectStatus.CORRECT -> CustomColor.SuccessOutline
                        CorrectStatus.PARTIAL -> CustomColor.WarningOutline
                        else -> CustomColor.ErrorOutline
                    })
                    .size(70.dp)
                    .padding(4.dp),
                contentAlignment = Alignment.Center
            ) {
                if (field.status == CorrectStatus.HIGHER) {
                    Icon(
                        imageVector = Icons.Rounded.ArrowUpward,
                        modifier = Modifier.fillMaxSize().scale(1.5f),
                        contentDescription = "Higher",
                        tint = CustomColor.ErrorOutline
                    )
                }

                if (field.status == CorrectStatus.LOWER) {
                    Icon(
                        imageVector = Icons.Rounded.ArrowDownward,
                        modifier = Modifier.fillMaxSize().scale(1.5f),
                        contentDescription = "Lower",
                        tint = CustomColor.ErrorOutline
                    )
                }

                Text(
                    text = field.fieldValue,
                    style = MaterialTheme.typography.bodySmall,
                    textAlign = TextAlign.Center
                )
            }

        }

    }

}