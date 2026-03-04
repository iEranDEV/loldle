package me.narei.loldle.data

import android.content.Context
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.withContext
import kotlinx.serialization.json.Json
import java.io.ByteArrayOutputStream

sealed interface LoadingState {
    data object Idle : LoadingState
    data class Loading(val progress: Float) : LoadingState
    data object Success : LoadingState
    data class Error(val message: String) : LoadingState
}

class ChampionRepository(private val context: Context) {

    private val json = Json { ignoreUnknownKeys = true }

    private val _loadingState = MutableStateFlow<LoadingState>(LoadingState.Idle)
    val loadingState: StateFlow<LoadingState> = _loadingState.asStateFlow()

    private var champions: List<Champion> = emptyList()

    suspend fun loadChampionsData() = withContext(Dispatchers.IO) {
        try {
            _loadingState.value = LoadingState.Loading(0f)

            val inputStream = context.assets.open("champions.json")
            val totalBytes = inputStream.available().toFloat()
            val outputStream = ByteArrayOutputStream()
            val buffer = ByteArray(4096)

            var bytesCopied = 0
            var bytes = inputStream.read(buffer)

            while (bytes >= 0) {
                outputStream.write(buffer, 0, bytes)
                bytesCopied += bytes

                _loadingState.value = LoadingState.Loading(bytesCopied / totalBytes)

                delay(10)

                bytes = inputStream.read(buffer)
            }

            val jsonString = outputStream.toString("UTF-8")
            champions = json.decodeFromString(jsonString)

            _loadingState.value = LoadingState.Success

        } catch (e: Exception) {
            _loadingState.value = LoadingState.Error(e.localizedMessage ?: "Wystąpił błąd")
        }
    }

    fun getAllChampions(): List<Champion> = champions

}