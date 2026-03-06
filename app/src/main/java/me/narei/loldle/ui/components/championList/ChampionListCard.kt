package me.narei.loldle.ui.components.championList

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import me.narei.loldle.data.Champion
import me.narei.loldle.ui.theme.spacing

@Composable
fun ChampionListCard(
    modifier: Modifier = Modifier,
    champion: Champion
) {

    Row(
        modifier = modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.surface)
            .padding(MaterialTheme.spacing.small),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.large)
    ) {
        AsyncImage(
            model = champion.iconUrl,
            contentDescription = "Ikona ${champion.name}",
            modifier = Modifier
                .size(50.dp)
        )

        Column {
            Text(
                text = champion.name,
                style = MaterialTheme.typography.titleLarge
            )

            Text(
                text = champion.title,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSecondary
            )
        }
    }

}