package me.narei.loldle.ui.components.shared

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import me.narei.loldle.ui.theme.spacing

@Composable
fun AppButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    icon: ImageVector? = null,
    title: String
) {

    Box(
        modifier = modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .background(MaterialTheme.colorScheme.surface)
            .border(4.dp, MaterialTheme.colorScheme.outline)
            .padding(vertical = MaterialTheme.spacing.medium),
        contentAlignment = Alignment.Center
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.medium),
            verticalAlignment = Alignment.CenterVertically
        ) {
            if (icon != null) {
                Icon(
                    imageVector = icon,
                    contentDescription = "Guess Champion",
                    tint = MaterialTheme.colorScheme.primary
                )
            }

            Text(
                text = title,
                style = MaterialTheme.typography.titleLarge,
                fontSize = 20.sp
            )
        }
    }

}