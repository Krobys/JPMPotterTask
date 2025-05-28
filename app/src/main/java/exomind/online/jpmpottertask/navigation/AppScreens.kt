package exomind.online.jpmpottertask.navigation

import kotlinx.serialization.Serializable

sealed interface AppScreens {

    @Serializable
    object CharacterList: AppScreens

    @Serializable
    data class CharacterDetails(val id: String): AppScreens

}
