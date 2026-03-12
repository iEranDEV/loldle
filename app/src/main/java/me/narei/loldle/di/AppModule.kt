package me.narei.loldle.di

import me.narei.loldle.data.ChampionRepository
import me.narei.loldle.ui.screens.championDetail.ChampionDetailViewModel
import me.narei.loldle.ui.screens.championList.ChampionListViewModel
import me.narei.loldle.ui.screens.games.gameAbility.GameAbilityViewModel
import me.narei.loldle.ui.screens.games.gameChampion.GameChampionViewModel
import me.narei.loldle.ui.screens.games.gameSkin.GameSkinViewModel
import me.narei.loldle.ui.screens.loading.LoadingViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val appModule = module {

    single<ChampionRepository> { ChampionRepository(androidContext()) }

    viewModel { LoadingViewModel(get()) }
    viewModel { ChampionListViewModel(get()) }
    viewModel { GameChampionViewModel(get()) }
    viewModel { GameAbilityViewModel(get()) }
    viewModel { GameSkinViewModel(get()) }
    viewModel { (championId: String) -> ChampionDetailViewModel(championId, get()) }

}