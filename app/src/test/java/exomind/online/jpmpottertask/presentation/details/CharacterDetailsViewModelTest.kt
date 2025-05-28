package exomind.online.jpmpottertask.presentation.details


import exomind.online.jpmpottertask.domain.models.Character
import exomind.online.jpmpottertask.domain.details.GetCharacterUseCase
import exomind.online.jpmpottertask.presentation.details.CharacterDetailsViewModel.State
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
class CharacterDetailsViewModelTest {

    private val testDispatcher = StandardTestDispatcher()
    private val getCharacterUseCase: GetCharacterUseCase = mockk()
    private lateinit var viewModel: CharacterDetailsViewModel

    @Before
    fun setup() {
        MockKAnnotations.init(this)
        Dispatchers.setMain(testDispatcher)
        viewModel = CharacterDetailsViewModel(getCharacterUseCase)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `initial state is Loading`() = runTest {
        val state = viewModel.character.first()
        assertTrue(state is State.Loading)
    }

    @Test
    fun `requestCharacter success emits Success state`() = runTest {
        // GIVEN
        val id = "123"
        val character = mockk<Character>()
        coEvery { getCharacterUseCase(id) } returns character

        // WHEN
        viewModel.requestCharacter(id)
        advanceUntilIdle()

        // THEN
        val state = viewModel.character.first() as? State.Success
        assertEquals(character, state?.character)
        coVerify { getCharacterUseCase(id) }
    }

    @Test
    fun `requestCharacter failure emits Error state`() = runTest {
        // GIVEN
        val id = "456"
        coEvery { getCharacterUseCase(id) } throws RuntimeException("error")

        // WHEN
        viewModel.requestCharacter(id)
        advanceUntilIdle()

        // THEN
        val state = viewModel.character.first() as? State.Error
        assertEquals("error", state?.message)
    }

    @Test
    fun `onEvent NavigateBack emits NavigateBack effect`() = runTest {
        // WHEN
        var received: CharacterDetailsViewModel.Effect? = null
        val job = launch { viewModel.effects.collect { received = it } }
        viewModel.onEvent(CharacterDetailsViewModel.Event.NavigateBack)
        advanceUntilIdle()

        // THEN
        assertTrue(received is CharacterDetailsViewModel.Effect.NavigateBack)
        job.cancel()
    }
}
