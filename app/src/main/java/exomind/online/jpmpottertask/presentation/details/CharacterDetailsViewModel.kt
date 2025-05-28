package exomind.online.jpmpottertask.presentation.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import exomind.online.jpmpottertask.domain.details.GetCharacterUseCase
import exomind.online.jpmpottertask.presentation.details.model.Effect
import exomind.online.jpmpottertask.presentation.details.model.Event
import exomind.online.jpmpottertask.presentation.details.model.UIState
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

    private val _character = MutableStateFlow<UIState>(UIState.Loading)
    val character = _character.asStateFlow()

    private val _effects = MutableSharedFlow<Effect>()
    val effects = _effects.asSharedFlow()

    fun requestCharacter(id: String) {
        viewModelScope.launch {
            runCatching {
                getCharacterUseCase(id)
            }.onSuccess { character ->
                _character.emit(UIState.Success(character))
            }.onFailure {
                _character.emit(UIState.Error(it.message ?: "Unknown error"))
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

}
