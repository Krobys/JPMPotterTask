package exomind.online.jpmpottertask.presentation.characters.model

import exomind.online.jpmpottertask.domain.models.Character

sealed interface Event {
        class QueryCharacters(val query: String) : Event
        object RetryCharacters: Event
        object ScrollToTop: Event
        class NavigateCharacterDetails(val character: Character) : Event
    }