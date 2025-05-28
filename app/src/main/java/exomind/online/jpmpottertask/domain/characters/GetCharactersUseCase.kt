package exomind.online.jpmpottertask.domain.characters

import exomind.online.jpmpottertask.domain.models.Character
import exomind.online.jpmpottertask.domain.CharactersRepository
import javax.inject.Inject

class GetCharactersUseCase @Inject constructor(
    private val repository: CharactersRepository,
) {
    suspend operator fun invoke(query: String? = null): List<Character> {
        return repository.getCharacters(query)
    }
}