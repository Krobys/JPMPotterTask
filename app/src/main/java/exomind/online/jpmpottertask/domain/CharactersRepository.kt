package exomind.online.jpmpottertask.domain

import exomind.online.jpmpottertask.domain.models.Character

interface CharactersRepository {
    suspend fun getCharacters(query: String?): List<Character>
    suspend fun getCharacter(id: String): Character
}