package exomind.online.jpmpottertask.navigation

import kotlinx.serialization.Serializable

sealed interface AppScreen {

    @Serializable
    object CharacterList : AppScreen

    @Serializable
    data class CharacterDetails(val id: String) : AppScreen

}
