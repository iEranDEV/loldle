package me.narei.loldle.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider

private val ColorScheme = darkColorScheme(
    primary = CustomColor.Primary,
    background = CustomColor.Background,
    surface = CustomColor.SecondaryBackground,
    onBackground = CustomColor.Text,
    onSurface = CustomColor.Text,
    onSecondary = CustomColor.SecondaryText,
    outline = CustomColor.Outline
)

@Composable
fun LoldleTheme(
    content: @Composable () -> Unit
) {

    CompositionLocalProvider(
        LocalSpacing provides Spacing()
    ) {
        MaterialTheme(
            colorScheme = ColorScheme,
            typography = Typography,
            content = content,
        )
    }
}