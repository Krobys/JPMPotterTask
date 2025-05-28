package exomind.online.jpmpottertask.presentation.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import exomind.online.jpmpottertask.domain.Character
import exomind.online.jpmpottertask.domain.details.GetCharacterUseCase
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CharacterDetailsViewModel @Inject constructor(
    private val getCharacterUseCase: GetCharacterUseCase,
) : ViewModel() {

    private val _character = MutableStateFlow<State>(State.Loading)
    val character = _character.asStateFlow()

    private val _effects = MutableSharedFlow<Effect>()
    val effects = _effects.asSharedFlow()

    fun requestCharacter(id: String) {
        viewModelScope.launch {
            runCatching {
                getCharacterUseCase(id)
            }.onSuccess { character ->
                _character.emit(State.Success(character))
            }.onFailure {
                _character.emit(State.Error(it.message ?: "Unknown error"))
            }
        }
    }

    fun onEvent(event: Event) {
        when (event) {
            is Event.NavigateBack -> navigateBack()
        }
    }

    private fun navigateBack() {
        viewModelScope.launch {
            _effects.emit(Effect.NavigateBack)
        }
    }

    sealed interface Effect {
        object NavigateBack: Effect
    }

    sealed interface Event {
        object NavigateBack : Event
    }

    sealed interface State {
        object Loading : State
        data class Success(val character: Character) : State
        data class Error(val message: String) : State
    }
}
