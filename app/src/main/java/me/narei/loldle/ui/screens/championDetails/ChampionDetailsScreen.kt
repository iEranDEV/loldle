package me.narei.loldle.ui.screens.championDetails

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import me.narei.loldle.data.ChampionRepository
import org.koin.compose.koinInject

@Composable
fun ChampionDetailsScreen(
    modifier: Modifier = Modifier,
    id: String,
    championRepository: ChampionRepository = koinInject()
) {

    val champion = remember(id) { championRepository.getChampionById(id) }

    Scaffold() { innerPadding ->
        Column(
            modifier = modifier.padding(innerPadding)
        ) {
            if (champion == null) {
                Text("Not found")
            } else {
                Text(champion.name)
            }
        }
    }

}