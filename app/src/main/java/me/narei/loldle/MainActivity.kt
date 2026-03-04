package me.narei.loldle

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import me.narei.loldle.ui.screens.NavigationRoot
import me.narei.loldle.ui.theme.LoldleTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            LoldleTheme {
                NavigationRoot()
            }
        }
    }
}
