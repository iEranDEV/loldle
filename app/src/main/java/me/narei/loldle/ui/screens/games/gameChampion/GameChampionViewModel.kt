package me.narei.loldle.ui.screens.games.gameChampion

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import me.narei.loldle.data.ChampionRepository

class GameChampionViewModel(
    private val championRepository: ChampionRepository
) : ViewModel() {
    
    val champions = championRepository.getAllChampions()
    var championToGuess = champions.random()

    private val _guesses = MutableStateFlow<List<GameChampionGuess>>(emptyList())
    val guesses: StateFlow<List<GameChampionGuess>> = _guesses.asStateFlow()

    private val _isGameWon = MutableStateFlow(false)
    val isGameWon: StateFlow<Boolean> = _isGameWon.asStateFlow()

    fun guessChampion(championId: String) {
        val champion = champions.find { it.id == championId } ?: return

        _guesses.value += GameChampionGuess(
            championId = championId,
            iconUrl = champion.iconUrl,
            fields = listOf(
                compareStringField("gender", champion.gender, championToGuess.gender),
                compareListField("positions", champion.positions, championToGuess.positions),
                compareListField("species", champion.species, championToGuess.species),
                compareStringField("resource", champion.resource, championToGuess.resource),
                compareListField("regions", champion.regions, championToGuess.regions),
                compareListField("attackType", champion.attackType, championToGuess.attackType),
                compareYearField("releaseYear", champion.releaseYear, championToGuess.releaseYear)
            )
        )

        if (championId == championToGuess.id) {
            _isGameWon.value = true
        }
    }

    fun resetGame() {
        championToGuess = champions.random()
        _guesses.value = emptyList()
        _isGameWon.value = false
    }

}
