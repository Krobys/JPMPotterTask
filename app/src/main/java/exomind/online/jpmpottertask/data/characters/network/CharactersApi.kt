package exomind.online.jpmpottertask.data.characters.network

import retrofit2.http.GET

interface CharactersApi {
    @GET("characters")
    suspend fun fetchCharacters(): List<CharacterDto>
}