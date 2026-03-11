package me.narei.loldle.ui.screens.championDetail.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import me.narei.loldle.ui.theme.spacing

@Composable
fun ChampionDetailRow(
    modifier: Modifier = Modifier,
    title: String,
    content: @Composable () -> Unit
) {
    Row(
        modifier = modifier.fillMaxWidth()
    ) {
        Text(
            text = title,
            color = MaterialTheme.colorScheme.onSecondary,
            modifier = Modifier.weight(0.4f)
        )
        Row (
            modifier = Modifier.weight(0.6f),
            horizontalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.small),
            verticalAlignment = Alignment.CenterVertically
        ) {
            content()
        }
    }
}