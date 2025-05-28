package exomind.online.jpmpottertask.presentation.characters

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import exomind.online.jpmpottertask.domain.characters.GetCharactersUseCase
import exomind.online.jpmpottertask.domain.models.Character
import exomind.online.jpmpottertask.presentation.characters.model.Effect
import exomind.online.jpmpottertask.presentation.characters.model.Event
import exomind.online.jpmpottertask.presentation.characters.model.UIState
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import javax.inject.Inject

@OptIn(FlowPreview::class)
@HiltViewModel
class CharactersListViewModel @Inject constructor(
    private val getCharactersUseCase: GetCharactersUseCase,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _characters = MutableStateFlow<UIState>(UIState.Loading)
    val characters = _characters.asStateFlow()

    private val _effects = MutableSharedFlow<Effect>()
    val effects = _effects.asSharedFlow()

    private val _query = savedStateHandle.getMutableStateFlow("query", "")
    val query = _query.asStateFlow()

    init {
        viewModelScope.launch {
            query.debounce(300)
                .distinctUntilChanged()
                .collect { query ->
                    fetchCharacters(query)
                }
        }
        viewModelScope.launch {
            fetchCharacters()
        }
    }

    fun onEvent(event: Event) {
        when (event) {
            Event.RetryCharacters -> retryCharacters()
            Event.ScrollToTop -> scrollToTop()
            is Event.QueryCharacters -> queryCharacters(event.query)
            is Event.NavigateCharacterDetails -> navigateToCharacterDetails(event.character)
        }
    }

    private suspend fun fetchCharacters(query: String? = null) {
        runCatching {
            getCharactersUseCase(query)
        }.onSuccess { characters ->
            _characters.emit(UIState.Success(characters))
        }.onFailure {
            _characters.emit(UIState.Error(it.message ?: "Unknown error"))
        }
    }

    private fun navigateToCharacterDetails(character: Character) {
        viewModelScope.launch {
            _effects.emit(Effect.NavigateToCharacterDetails(character))
        }
    }

    private fun queryCharacters(query: String) {
        viewModelScope.launch {
            _query.emit(query)
        }
    }

    private fun retryCharacters() {
        viewModelScope.launch {
            _characters.emit(UIState.Loading)
            fetchCharacters()
        }
    }

    private fun scrollToTop() {
        viewModelScope.launch {
            _effects.emit(Effect.ScrollToTop)
        }
    }

}
