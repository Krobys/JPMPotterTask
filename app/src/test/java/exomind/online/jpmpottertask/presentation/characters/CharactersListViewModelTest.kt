package exomind.online.jpmpottertask.presentation.characters

import androidx.lifecycle.SavedStateHandle
import exomind.online.jpmpottertask.domain.models.Character
import exomind.online.jpmpottertask.domain.characters.GetCharactersUseCase
import exomind.online.jpmpottertask.presentation.characters.model.Effect
import exomind.online.jpmpottertask.presentation.characters.model.Event
import exomind.online.jpmpottertask.presentation.characters.model.UIState
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class CharactersListViewModelTest {

    private val testDispatcher = StandardTestDispatcher()

    private val getCharactersUseCase: GetCharactersUseCase = mockk()

    private lateinit var savedStateHandle: SavedStateHandle
    private lateinit var viewModel: CharactersListViewModel

    @Before
    fun setup() {
        MockKAnnotations.init(this)
        Dispatchers.setMain(testDispatcher)
        savedStateHandle = SavedStateHandle()
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `initial fetch success emits Success UIState`() = runTest {
        // GIVEN
        val characters = listOf(mockk<Character>(), mockk<Character>())
        coEvery { getCharactersUseCase(any()) } returns characters

        // WHEN
        viewModel = CharactersListViewModel(getCharactersUseCase, savedStateHandle)
        advanceUntilIdle()

        // THEN
        val state = viewModel.characters.first() as UIState.Success
        assertEquals(characters, state.characters)
    }

    @Test
    fun `initial fetch failure emits Error UIState`() = runTest {
        // GIVEN
        coEvery { getCharactersUseCase(any()) } throws RuntimeException("fail")

        // WHEN
        viewModel = CharactersListViewModel(getCharactersUseCase, savedStateHandle)
        advanceUntilIdle()

        // THEN
        val state = viewModel.characters.first() as? UIState.Error
        assertEquals("fail", state?.message)
    }

    @Test
    fun `retryCharacters triggers useCase again`() = runTest {
        // GIVEN
        coEvery { getCharactersUseCase(any()) } returns emptyList()

        // WHEN
        viewModel = CharactersListViewModel(getCharactersUseCase, savedStateHandle)
        advanceUntilIdle()
        viewModel.onEvent(Event.RetryCharacters)
        advanceUntilIdle()

        // THEN
        coVerify(atLeast = 2) { getCharactersUseCase() }
    }

    @Test
    fun `queryCharacters after debounce calls useCase with query`() = runTest {
        // GIVEN
        val query = "Ron"
        coEvery { getCharactersUseCase(query) } returns listOf()

        // WHEN
        viewModel = CharactersListViewModel(getCharactersUseCase, savedStateHandle)
        advanceUntilIdle()
        viewModel.onEvent(Event.QueryCharacters(query))
        advanceUntilIdle()

        // THEN
        coVerify { getCharactersUseCase(query) }
    }

    @Test
    fun `navigateToCharacterDetails emits effect`() = runTest {
        // GIVEN
        val character = mockk<Character>()
        coEvery { getCharactersUseCase(any()) } returns emptyList()
        viewModel = CharactersListViewModel(getCharactersUseCase, savedStateHandle)

        // WHEN
        var received: Effect? = null
        val job = launch { viewModel.effects.collect { received = it } }
        viewModel.onEvent(Event.NavigateCharacterDetails(character))
        advanceUntilIdle()

        // THEN
        assertTrue(received is Effect.NavigateToCharacterDetails)
        assertEquals(character, (received as Effect.NavigateToCharacterDetails).character)
        job.cancel()
    }
}
