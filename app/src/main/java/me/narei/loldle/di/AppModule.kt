package me.narei.loldle.di

import me.narei.loldle.data.ChampionRepository
import me.narei.loldle.ui.screens.championList.ChampionListViewModel
import me.narei.loldle.ui.screens.games.gameChampion.GameChampionViewModel
import me.narei.loldle.ui.screens.splash.SplashViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val appModule = module {

    single<ChampionRepository> { ChampionRepository(androidContext()) }

    viewModel { SplashViewModel(get()) }
    viewModel { ChampionListViewModel(get()) }
    viewModel { GameChampionViewModel(get()) }

}