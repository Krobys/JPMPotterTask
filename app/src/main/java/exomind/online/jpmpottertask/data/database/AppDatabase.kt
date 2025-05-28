package exomind.online.jpmpottertask.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import exomind.online.jpmpottertask.data.characters.CharacterEntity
import exomind.online.jpmpottertask.data.characters.CharactersDao

@Database(
    entities = [
        CharacterEntity::class
    ],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun charactersDao(): CharactersDao
}