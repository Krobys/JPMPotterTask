package exomind.online.jpmpottertask.data.characters.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface CharactersDao {

    @Query("SELECT * FROM CharacterEntity")
    suspend fun getCharacters(): List<CharacterEntity>

    @Query("SELECT * FROM CharacterEntity WHERE id = :id")
    suspend fun getCharacter(id: String): CharacterEntity

    @Insert
    suspend fun insertCharacters(characters: List<CharacterEntity>)

}