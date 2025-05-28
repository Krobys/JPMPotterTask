package exomind.online.jpmpottertask.data.characters

import exomind.online.jpmpottertask.data.network.CharacterDto
import exomind.online.jpmpottertask.domain.Character
import io.mockk.every
import io.mockk.mockk
import org.junit.Assert.assertEquals
import org.junit.Test

class CharacterMapperTest {

    private val mapper = CharacterMapper()

    @Test
    fun `maps dto to entity correctly`() {
        // GIVEN
        val dto: CharacterDto = mockk()
        every { dto.name }.returns("Hermione Granger")
        every { dto.image }.returns("herm_url")
        every { dto.actor }.returns("Emma Watson")
        every { dto.species }.returns("Human")

        val expectedEntity = CharacterEntity(
            characterName = "Hermione Granger",
            imageUrl = "herm_url",
            actorName = "Emma Watson",
            species = "Human"
        )

        // WHEN
        val actualEntity = mapper.fromDto(dto)

        // THEN
        assertEquals(expectedEntity, actualEntity)
    }

    @Test
    fun `maps entity to domain correctly`() {
        // GIVEN
        val entity = CharacterEntity(
            characterName = "Harry Potter",
            imageUrl = "harry_url",
            actorName = "Daniel Radcliffe",
            species = "Wizard"
        )
        val expectedDomain = Character(
            characterName = "Harry Potter",
            actorName = "Daniel Radcliffe",
            imageUrl = "harry_url",
            species = "Wizard"
        )

        // WHEN
        val actualDomain = mapper.toDomain(entity)

        // THEN
        assertEquals(expectedDomain, actualDomain)
    }
}
