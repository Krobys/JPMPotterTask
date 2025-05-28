package exomind.online.jpmpottertask.data.characters.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class CharacterEntity(
    @PrimaryKey val id: String,
    val characterName: String,
    val actorName: String,
    val imageUrl: String,
    val species: String,
    val house: String,
    val dateOfBirth: String?,
    val alive: Boolean
)