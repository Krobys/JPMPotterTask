package exomind.online.jpmpottertask.data.characters

import exomind.online.jpmpottertask.data.characters.local.CharacterEntity
import exomind.online.jpmpottertask.data.characters.local.CharactersDao
import exomind.online.jpmpottertask.data.characters.mappers.CharacterMapper
import exomind.online.jpmpottertask.data.characters.network.CharactersApi
import exomind.online.jpmpottertask.domain.models.Character
import exomind.online.jpmpottertask.domain.CharactersRepository
import javax.inject.Inject
import kotlin.collections.map

class CharactersRepositoryImpl @Inject constructor(
    private val api: CharactersApi,
    private val dao: CharactersDao,
    private val mapper: CharacterMapper,
) : CharactersRepository {

    override suspend fun getCharacters(query: String?): List<Character> {
        val cached = dao.getCharacters()
        if (cached.isEmpty()) {
            val entities = api.fetchCharacters()
                .map(mapper::fromDto)
            dao.insertCharacters(entities)
        }

        return dao.getCharacters()
            .filterBy(query)
            .map(mapper::toDomain)
    }

    override suspend fun getCharacter(id: String): Character {
        val entity = dao.getCharacter(id)
        return mapper.toDomain(entity)
    }

    private fun List<CharacterEntity>.filterBy(query: String?): List<CharacterEntity> =
        if (query.isNullOrBlank()) this
        else filter {
            it.characterName.contains(query, ignoreCase = true) ||
                it.actorName.contains(query, ignoreCase = true)
        }
}
