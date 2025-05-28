package exomind.online.jpmpottertask.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import exomind.online.jpmpottertask.data.characters.CharactersDao
import exomind.online.jpmpottertask.data.database.AppDatabase
import javax.inject.Singleton
import kotlin.jvm.java

@InstallIn(SingletonComponent::class)
@Module
class DatabaseModule {

    @Singleton
    @Provides
    fun provideDatabase(
        @ApplicationContext context: Context,
    ): AppDatabase {
        return Room.databaseBuilder(
            context = context,
            klass = AppDatabase::class.java,
            name = "app_database",
        ).build()
    }

    @Singleton
    @Provides
    fun provideCharactersDao(
        database: AppDatabase,
    ): CharactersDao {
        return database.charactersDao()
    }
}