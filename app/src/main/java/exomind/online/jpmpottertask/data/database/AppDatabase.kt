package exomind.online.jpmpottertask.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import exomind.online.jpmpottertask.data.characters.local.CharacterEntity
import exomind.online.jpmpottertask.data.characters.local.CharactersDao

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