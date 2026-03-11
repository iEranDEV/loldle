package me.narei.loldle.ui.screens.games.gameAbility.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import me.narei.loldle.ui.screens.games.gameAbility.GameAbilityGuess
import me.narei.loldle.ui.theme.CustomColor
import me.narei.loldle.ui.theme.spacing

@Composable
fun GameAbilityGuessCard(
    modifier: Modifier = Modifier,
    guess: GameAbilityGuess
) {

    Row(
        modifier = modifier
            .fillMaxWidth()
            .background(if (guess.correct) CustomColor.Success else CustomColor.Error)
            .border(2.dp, if (guess.correct) CustomColor.SuccessOutline else CustomColor.ErrorOutline)
            .padding(MaterialTheme.spacing.medium),
        horizontalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.medium)
    ) {

        AsyncImage(
            model = guess.iconUrl,
            contentDescription = "Icon ${guess.id}",
            modifier = Modifier
                .size(40.dp)
        )

        Text(
            text = guess.name,
            style = MaterialTheme.typography.titleLarge
        )


    }

}