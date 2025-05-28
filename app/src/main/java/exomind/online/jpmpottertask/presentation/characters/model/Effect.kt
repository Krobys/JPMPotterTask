package exomind.online.jpmpottertask.presentation.characters.model

import exomind.online.jpmpottertask.domain.models.Character

sealed interface Effect {
        data class NavigateToCharacterDetails(val character: Character) : Effect
        data object ScrollToTop : Effect
    }