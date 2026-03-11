package me.narei.loldle.ui.screens.championList

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import me.narei.loldle.ui.screens.championList.components.ChampionListCard
import me.narei.loldle.ui.theme.spacing
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChampionListScreen(
    viewModel: ChampionListViewModel = koinViewModel(),
    navigateToDetails: (String) -> Unit,
    backToHome: () -> Unit
) {

    val state by viewModel.state.collectAsStateWithLifecycle()

    val focusRequester = remember { FocusRequester() }
    val focusManager = LocalFocusManager.current

    LaunchedEffect(state.isSearchActive) {
        if (state.isSearchActive) {
            focusRequester.requestFocus()
        } else {
            focusManager.clearFocus()
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    if (state.isSearchActive) {
                        TextField(
                            value = state.searchQuery,
                            onValueChange = { viewModel.onAction(ChampionListAction.UpdateSearchQuery(it)) },
                            placeholder = { Text("Search champion...") },
                            singleLine = true,
                            modifier = Modifier
                                .fillMaxWidth()
                                .focusRequester(focusRequester),
                            colors = TextFieldDefaults.colors(
                                focusedContainerColor = Color.Transparent,
                                unfocusedContainerColor = Color.Transparent,
                                disabledContainerColor = Color.Transparent,
                                focusedIndicatorColor = Color.Transparent,
                                unfocusedIndicatorColor = Color.Transparent
                            )
                        )
                    } else {
                        Text("List of champions")
                    }
                },
                navigationIcon = {
                    IconButton(
                        onClick = {
                            if (state.isSearchActive) {
                                viewModel.onAction(ChampionListAction.ToggleSearch)
                            } else {
                                backToHome()
                            }
                        }
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Rounded.ArrowBack,
                            contentDescription = "Back to home"
                        )
                    }
                },
                actions = {
                    if (state.isSearchActive) {
                        IconButton(
                            onClick = {
                                if (state.searchQuery.isNotEmpty()) {
                                    viewModel.onAction(ChampionListAction.UpdateSearchQuery(""))
                                } else {
                                    viewModel.onAction(ChampionListAction.ToggleSearch)
                                }
                            }
                        ) {
                            Icon(imageVector = Icons.Rounded.Close, contentDescription = "Close search")
                        }
                    } else {
                        IconButton(
                            onClick = { viewModel.onAction(ChampionListAction.ToggleSearch) }
                        ) {
                            Icon(imageVector = Icons.Rounded.Search, contentDescription = "Search")
                        }
                    }
                }
            )
        }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .padding(innerPadding),
            contentPadding = PaddingValues(MaterialTheme.spacing.medium),
            verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.small)
        ) {
            items(
                items = state.champions,
                key = { it.id }
            ) { champion ->
                ChampionListCard(
                    champion = champion,
                    onClick = {
                        focusManager.clearFocus()
                        navigateToDetails(champion.id)
                    }
                )
            }
        }
    }

}