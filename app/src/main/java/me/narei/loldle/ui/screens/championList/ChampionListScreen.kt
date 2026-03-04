package me.narei.loldle.ui.screens.championList

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import me.narei.loldle.ui.components.championList.ChampionListCard
import org.koin.androidx.compose.koinViewModel

@Composable
fun ChampionListScreen(
    viewModel: ChampionListViewModel = koinViewModel(),
    navigateToDetails: (String) -> Unit,
    backToHome: () -> Unit
) {

    val champions by viewModel.champions.collectAsStateWithLifecycle()

    Scaffold { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .padding(innerPadding)
                .padding(10.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            items(items = champions, key = { it.id }) { champion ->
                ChampionListCard(
                    champion = champion
                )
            }
        }
    }

}