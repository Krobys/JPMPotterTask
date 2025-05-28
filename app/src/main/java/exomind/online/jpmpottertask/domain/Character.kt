package exomind.online.jpmpottertask.domain

data class Character(
    val characterName: String,
    val actorName: String,
    val imageUrl: String,
    val species: String,
    val house: String,
    val dateOfBirth: String? = null
)