package exomind.online.jpmpottertask.domain.details

import exomind.online.jpmpottertask.domain.models.Character
import exomind.online.jpmpottertask.domain.CharactersRepository
import javax.inject.Inject

class GetCharacterUseCase @Inject constructor(
    private val repository: CharactersRepository,
) {
    suspend operator fun invoke(id: String): Character {
        return repository.getCharacter(id)
    }
}