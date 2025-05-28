package exomind.online.jpmpottertask.presentation.characters.model

import androidx.compose.runtime.Stable
import exomind.online.jpmpottertask.domain.models.Character

sealed interface UIState {
    object Loading : UIState

    @Stable
    data class Success(val characters: List<Character>) : UIState
    data class Error(val message: String) : UIState
}