package me.narei.loldle.ui.screens.championDetail

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material.icons.rounded.Female
import androidx.compose.material.icons.rounded.Male
import androidx.compose.material.icons.rounded.QuestionMark
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import me.narei.loldle.ui.components.shared.AppButton
import me.narei.loldle.ui.screens.championDetail.components.ChampionDetailRow
import me.narei.loldle.ui.screens.championDetail.components.ChampionDetailSection
import me.narei.loldle.ui.theme.spacing
import org.koin.androidx.compose.koinViewModel
import org.koin.core.parameter.parametersOf

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChampionDetailScreen(
    modifier: Modifier = Modifier,
    championId: String,
    viewModel: ChampionDetailViewModel = koinViewModel { parametersOf(championId) },
    navigateBack: () -> Unit
) {

    val champion = viewModel.champion

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(champion?.name ?: "Not found") },
                navigationIcon = {
                    IconButton(
                        onClick = { navigateBack() }
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Rounded.ArrowBack,
                            contentDescription = "Back to list"
                        )
                    }
                }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = modifier
                .padding(innerPadding)
        ) {

            if (champion == null) {
                Column (
                    modifier = Modifier.fillMaxSize().padding(MaterialTheme.spacing.medium),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.medium, Alignment.CenterVertically)
                ) {
                    Text(
                        text = "Not found!\nChampion with id $championId not found.",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSecondary,
                        textAlign = TextAlign.Center
                    )

                    AppButton(
                        onClick = { navigateBack() },
                        title = "Back to list",
                        icon = Icons.AutoMirrored.Rounded.ArrowBack,
                    )
                }
            } else {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth(),
                    contentPadding = PaddingValues(MaterialTheme.spacing.medium),
                ) {

                    item {
                        Row(
                            modifier = modifier
                                .fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.large)
                        ) {
                            AsyncImage(
                                model = champion.iconUrl,
                                contentDescription = "Icon ${champion.name}",
                                modifier = Modifier.size(70.dp)
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

                        Spacer(modifier = Modifier.height(MaterialTheme.spacing.large))
                    }

                    item {
                        ChampionDetailSection(
                            title = "General info"
                        ) {

                            ChampionDetailRow(
                                title = "Name"
                            ) {
                                Text(champion.name)
                            }

                            ChampionDetailRow(
                                title = "Title"
                            ) {
                                Text(champion.title)
                            }

                            ChampionDetailRow(
                                title = "Gender"
                            ) {
                                Icon(
                                    imageVector = when (champion.gender) {
                                        "MALE" -> Icons.Rounded.Male
                                        "FEMALE" -> Icons.Rounded.Female
                                        else -> Icons.Rounded.QuestionMark
                                    },
                                    contentDescription = "Gender icon",
                                    tint = MaterialTheme.colorScheme.primary
                                )
                                Text(champion.gender.lowercase().replaceFirstChar { it.uppercase() })
                            }

                            ChampionDetailRow(
                                title = "Release year"
                            ) {
                                Text(champion.releaseYear.toString())
                            }

                            ChampionDetailRow(
                                title = "Resource"
                            ) {
                                Text(champion.resource)
                            }

                            ChampionDetailRow(
                                title = "Positions"
                            ) {
                                Column {
                                    champion.positions.forEach { position ->
                                        Text(position.lowercase().replaceFirstChar { it.uppercase() })
                                    }
                                }
                            }

                            ChampionDetailRow(
                                title = "Species"
                            ) {
                                Column {
                                    champion.species.forEach { species ->
                                        Text(species.lowercase().replaceFirstChar { it.uppercase() })
                                    }
                                }
                            }

                            ChampionDetailRow(
                                title = "Regions"
                            ) {
                                Column {
                                    champion.regions.forEach { region ->
                                        Text(region.lowercase().replaceFirstChar { it.uppercase() })
                                    }
                                }
                            }

                        }

                        Spacer(modifier = Modifier.height(MaterialTheme.spacing.large))
                    }

                    item {
                        ChampionDetailSection(
                            title = "Abilities"
                        ) {
                            champion.abilities.forEach { ability ->
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.medium)
                                ) {

                                    Text(
                                        modifier = Modifier.width(40.dp),
                                        text = ability.type.name,
                                        color = MaterialTheme.colorScheme.primary,
                                        style = MaterialTheme.typography.titleLarge
                                    )

                                    AsyncImage(
                                        model = ability.url,
                                        contentDescription = "Ability ${ability.name}",
                                        modifier = Modifier.size(40.dp)
                                    )

                                    Text(ability.name)

                                }
                            }
                        }

                        Spacer(modifier = Modifier.height(MaterialTheme.spacing.large))
                    }

                    item {
                        Text(
                            text = "Skins",
                            style = MaterialTheme.typography.titleMedium
                        )

                        Spacer(modifier = Modifier.height(MaterialTheme.spacing.small))
                    }

                    items(
                        items = champion.skins,
                        key = { skin -> skin.name }
                    ) { skin ->
                        Column {
                            AsyncImage(
                                model = skin.url,
                                contentDescription = "Skin ${skin.name}",
                                modifier = Modifier.fillMaxWidth().aspectRatio(16 / 9f)
                            )

                            Text(skin.name)

                            Spacer(modifier = Modifier.height(MaterialTheme.spacing.small))
                        }

                    }

                }
            }

        }
    }

}