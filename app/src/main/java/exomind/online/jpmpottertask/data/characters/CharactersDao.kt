package exomind.online.jpmpottertask.data.characters

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface CharactersDao {

    @Query("SELECT * FROM CharacterEntity")
    suspend fun getCharacters(): List<CharacterEntity>

    @Insert
    suspend fun insertCharacters(characters: List<CharacterEntity>)

}