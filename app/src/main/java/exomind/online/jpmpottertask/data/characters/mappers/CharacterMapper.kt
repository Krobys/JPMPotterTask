package exomind.online.jpmpottertask.data.characters.mappers

import exomind.online.jpmpottertask.data.characters.mappers.CharacterDateFormatter
import exomind.online.jpmpottertask.data.characters.local.CharacterEntity
import exomind.online.jpmpottertask.data.characters.network.CharacterDto
import exomind.online.jpmpottertask.domain.models.Character
import javax.inject.Inject

class CharacterMapper @Inject constructor(
    private val characterDateFormatter: CharacterDateFormatter,
) {
    fun fromDto(dto: CharacterDto): CharacterEntity =
        CharacterEntity(
            id = dto.id,
            characterName = dto.name,
            imageUrl = dto.image,
            actorName = dto.actor,
            species = dto.species,
            house = dto.house.ifBlank { "Unknown" },
            dateOfBirth = dto.dateOfBirth,
            alive = dto.alive
        )

    fun toDomain(entity: CharacterEntity): Character =
        Character(
            id = entity.id,
            characterName = entity.characterName,
            actorName = entity.actorName,
            imageUrl = entity.imageUrl,
            species = entity.species,
            house = entity.house.ifBlank { "Unknown" },
            dateOfBirth = characterDateFormatter.format(entity.dateOfBirth),
            alive = entity.alive
        )
}