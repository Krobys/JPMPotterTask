package exomind.online.jpmpottertask.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import exomind.online.jpmpottertask.data.characters.CharactersRepositoryImpl
import exomind.online.jpmpottertask.domain.CharactersRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class DataModule {

    @Binds
    @Singleton
    abstract fun provideCharactersRepository(
        impl: CharactersRepositoryImpl,
    ): CharactersRepository

}