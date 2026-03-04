package me.narei.loldle.data

import kotlinx.serialization.Serializable

@Serializable
data class Champion(
    val id: String,
    val name: String,
    val title: String,
    val iconUrl: String,
    val gender: String,
    val regions: List<String>,
    val species: List<String>,
    val releaseYear: Int,
    val skins: List<Skin>,
    val abilities: List<Ability>,
    val resource: String,
    val attackType: List<AttackType>,
    val positions: List<Position>
)

@Serializable
data class Skin(
    val name: String,
    val url: String
)

@Serializable
data class Ability(
    val type: AbilityType,
    val name: String,
    val url: String
)

@Serializable
enum class AbilityType {
    P,
    Q,
    W,
    E,
    R
}

@Serializable
enum class AttackType {
    MELEE,
    RANGED
}

@Serializable
enum class Position {
    TOP,
    JUNGLE,
    MID,
    BOTTOM,
    SUPPORT
}