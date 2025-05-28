package exomind.online.jpmpottertask.domain.models

data class Character(
    val id: String,
    val characterName: String,
    val actorName: String,
    val imageUrl: String,
    val species: String,
    val house: String,
    val dateOfBirth: String? = null,
    val alive: Boolean
)