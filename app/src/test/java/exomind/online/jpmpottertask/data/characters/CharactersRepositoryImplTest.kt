package exomind.online.jpmpottertask.data.characters

import exomind.online.jpmpottertask.data.network.CharacterDto
import exomind.online.jpmpottertask.data.network.CharactersApi
import exomind.online.jpmpottertask.domain.Character
import io.mockk.coEvery
import io.mockk.coJustRun
import io.mockk.coVerify
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

class CharactersRepositoryImplTest {

    private val api: CharactersApi = mockk()
    private val dao: CharactersDao = mockk()
    private val mapper: CharacterMapper = mockk()
    private val repository = CharactersRepositoryImpl(api, dao, mapper)

    private val dto1 = mockk<CharacterDto>()
    private val dto2 = mockk<CharacterDto>()
    val entity1 = CharacterEntity(
        id = "1",
        characterName = "Harry Potter",
        actorName = "Daniel Radcliffe",
        imageUrl = "",
        species = "",
        house = "house",
        dateOfBirth = "12-12-2012",
        alive = true,
    )
    val entity2 = CharacterEntity(
        id = "2",
        characterName = "Hermione Granger",
        actorName = "Emma Watson",
        imageUrl = "",
        species = "",
        house = "house",
        dateOfBirth = "12-12-2012",
        alive = true,
    )
    private val domain1: Character = mockk(relaxed = true)
    private val domain2: Character = mockk(relaxed = true)

    @Before
    fun setup() {
        coEvery { mapper.fromDto(dto1) } returns entity1
        coEvery { mapper.fromDto(dto2) } returns entity2
        coEvery { mapper.toDomain(entity1) } returns domain1
        coEvery { mapper.toDomain(entity2) } returns domain2
        coJustRun { dao.insertCharacters(any()) }
    }

    @Test
    fun `fetches from api and caches when cache is empty`() = runTest {
        // GIVEN
        coEvery { dao.getCharacters() } returnsMany listOf(emptyList(), listOf(entity1, entity2))
        coEvery { api.fetchCharacters() } returns listOf(dto1, dto2)

        // WHEN
        val result = repository.getCharacters(null)

        // THEN
        assertEquals(listOf(domain1, domain2), result)
        coVerify(exactly = 1) { api.fetchCharacters() }
        coVerify(exactly = 1) { dao.insertCharacters(listOf(entity1, entity2)) }
    }

    @Test
    fun `does not call api or cache when cache is not empty`() = runTest {
        // GIVEN
        coEvery { dao.getCharacters() } returns listOf(entity1, entity2)

        // WHEN
        val result = repository.getCharacters(null)

        // THEN
        assertEquals(listOf(domain1, domain2), result)
        coVerify(exactly = 0) { api.fetchCharacters() }
        coVerify(exactly = 0) { dao.insertCharacters(any()) }
    }

    @Test
    fun `fetches single character from cache`() = runTest {
        // GIVEN
        val id = "1"
        coEvery { dao.getCharacter(id) } returns entity1

        // WHEN
        val result = repository.getCharacter(id)

        // THEN
        assertEquals(result, domain1)
    }

    @Test(expected = Exception::class)
    fun `fetch single character thorws exception when no character`() = runTest {
        // GIVEN
        val id = "1"
        coEvery { dao.getCharacter(id) } throws Exception()

        // WHEN
        repository.getCharacter(id)
    }

    @Test
    fun `filters by character name query`() = runTest {
        // GIVEN
        coEvery { dao.getCharacters() } returns listOf(entity1, entity2)

        // WHEN
        val result = repository.getCharacters("herm")

        // THEN
        assertEquals(listOf(domain2), result)
    }

    @Test
    fun `filters by actor name query`() = runTest {
        // GIVEN
        coEvery { dao.getCharacters() } returns listOf(entity1, entity2)

        // WHEN
        val result = repository.getCharacters("daniel")

        // THEN
        assertEquals(listOf(domain1), result)
    }
}
