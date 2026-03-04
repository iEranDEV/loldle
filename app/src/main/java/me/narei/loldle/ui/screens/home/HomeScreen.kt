package me.narei.loldle.ui.screens.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun HomeScreen() {

    Scaffold() { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            Button(
                onClick = {}
            ) {
                Text("Guess Champion")
            }

            Button(
                onClick = {}
            ) {
                Text("Guess Spell")
            }

            Button(
                onClick = {}
            ) {
                Text("Guess Skin")
            }

            Button(
                onClick = {}
            ) {
                Text("List of champions")
            }
        }
    }

}