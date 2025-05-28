package exomind.online.jpmpottertask.domain

interface CharactersRepository {
    suspend fun getCharacters(query: String?): List<Character>
    suspend fun getCharacter(id: String): Character
}