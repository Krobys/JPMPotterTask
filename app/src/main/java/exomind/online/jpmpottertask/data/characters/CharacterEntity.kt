package exomind.online.jpmpottertask.data.characters

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class CharacterEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val characterName: String,
    val actorName: String,
    val imageUrl: String,
    val species: String,
    val house: String,
    val dateOfBirth: String?
)