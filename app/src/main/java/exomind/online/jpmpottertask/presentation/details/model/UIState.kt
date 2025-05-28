package exomind.online.jpmpottertask.presentation.details.model

import exomind.online.jpmpottertask.domain.models.Character

sealed interface UIState {
    object Loading : UIState
    data class Success(val character: Character) : UIState
    data class Error(val message: String) : UIState
}