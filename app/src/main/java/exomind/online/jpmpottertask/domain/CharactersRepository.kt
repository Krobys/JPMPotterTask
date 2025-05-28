package exomind.online.jpmpottertask.domain

interface CharactersRepository {
    suspend fun getCharacters(query: String?): List<Character>
}