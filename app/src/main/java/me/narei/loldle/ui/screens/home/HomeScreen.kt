package me.narei.loldle.ui.screens.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Image
import androidx.compose.material.icons.rounded.People
import androidx.compose.material.icons.rounded.Person
import androidx.compose.material.icons.rounded.Star
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.sp
import me.narei.loldle.ui.components.shared.AppButton
import me.narei.loldle.ui.screens.Screen
import me.narei.loldle.ui.theme.spacing

@Composable
fun HomeScreen(
    navigate: (Screen) -> Unit
) {

    Scaffold() { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(MaterialTheme.spacing.large),
            verticalArrangement = Arrangement.SpaceEvenly,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Loldle",
                    style = MaterialTheme.typography.titleLarge,
                    fontSize = 50.sp,
                    color = MaterialTheme.colorScheme.primary
                )
                Text(
                    text = "by Narei",
                    fontSize = 12.sp
                )
            }

            Column(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.medium),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                AppButton(
                    onClick = { navigate(Screen.GameChampion) },
                    title = "Guess Champion",
                    icon = Icons.Rounded.Person
                )

                AppButton(
                    onClick = { navigate(Screen.GameAbility) },
                    title = "Guess Ability",
                    icon = Icons.Rounded.Star
                )

                AppButton(
                    onClick = {},
                    title = "Guess Skin",
                    icon = Icons.Rounded.Image
                )

                AppButton(
                    onClick = { navigate(Screen.ChampionList) },
                    title = "List of champions",
                    icon = Icons.Rounded.People
                )

            }
        }
    }

}