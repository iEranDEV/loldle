package me.narei.loldle.ui.screens.games.gameChampion

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import me.narei.loldle.ui.components.shared.LazyDropdownMenu
import me.narei.loldle.ui.components.shared.LazyDropdownMenuOption
import me.narei.loldle.ui.theme.spacing
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GameChampionScreen(
    modifier: Modifier = Modifier,
    viewModel: GameChampionViewModel = koinViewModel(),
    backToHome: () -> Unit
) {

    val focusManager = LocalFocusManager.current

    val champions = viewModel.champions

    var selectedChampionId by remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Guess champion") },
                navigationIcon = {
                    IconButton(
                        onClick = {
                            focusManager.clearFocus()
                            backToHome()
                        }
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Rounded.ArrowBack,
                            contentDescription = "Back to home"
                        )
                    }
                }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = modifier
                .padding(innerPadding)
                .padding(MaterialTheme.spacing.medium)
        ) {
            Text("Guess Champion game")

            LazyDropdownMenu(
                options = champions.map { champion ->
                    LazyDropdownMenuOption(
                        key = champion.id,
                        title = champion.name,
                        icon = {
                            AsyncImage(
                                model = champion.iconUrl,
                                contentDescription = "Icon ${champion.name}",
                                modifier = Modifier
                                    .size(50.dp)
                            )
                        }
                    )
                },
                onOptionSelect = { key -> selectedChampionId = key }
            )

            Text(selectedChampionId)
        }
    }

}