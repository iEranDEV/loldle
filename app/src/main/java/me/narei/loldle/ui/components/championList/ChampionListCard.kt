package me.narei.loldle.ui.components.championList

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import me.narei.loldle.data.Champion

@Composable
fun ChampionListCard(
    modifier: Modifier = Modifier,
    champion: Champion
) {

    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        AsyncImage(
            model = champion.iconUrl,
            contentDescription = "Ikona ${champion.name}",
            modifier = Modifier
                .size(50.dp)
        )

        Text(text = champion.name)
    }

}