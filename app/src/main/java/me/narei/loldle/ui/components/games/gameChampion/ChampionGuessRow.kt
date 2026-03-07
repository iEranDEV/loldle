package me.narei.loldle.ui.components.games.gameChampion

import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowDownward
import androidx.compose.material.icons.rounded.ArrowUpward
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import me.narei.loldle.ui.screens.games.gameChampion.CorrectStatus
import me.narei.loldle.ui.screens.games.gameChampion.GameChampionGuess
import me.narei.loldle.ui.theme.spacing

@Composable
fun ChampionGuessRow(
    modifier: Modifier = Modifier,
    guess: GameChampionGuess
) {

    val scrollState = rememberScrollState()

    Row(
        modifier = modifier
            .fillMaxWidth()
            .horizontalScroll(scrollState)
            .padding(horizontal = MaterialTheme.spacing.medium),
        horizontalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.small)
    ) {

        AsyncImage(
            model = guess.iconUrl,
            contentDescription = "Icon ${guess.championId}",
            modifier = Modifier
                .size(75.dp)
        )

        guess.fields.forEach { field ->

            Box(
                modifier = Modifier
                    .background(when (field.status) {
                        CorrectStatus.CORRECT -> Color.Green
                        CorrectStatus.PARTIAL -> Color.Yellow
                        else -> Color.Red
                    })
                    .size(75.dp)
            ) {
                if (field.status == CorrectStatus.HIGHER) {
                    Icon(
                        imageVector = Icons.Rounded.ArrowUpward,
                        contentDescription = "Higher"
                    )
                }

                if (field.status == CorrectStatus.LOWER) {
                    Icon(
                        imageVector = Icons.Rounded.ArrowDownward,
                        contentDescription = "Lower"
                    )
                }

                Text(field.fieldValue)
            }

        }

    }

}