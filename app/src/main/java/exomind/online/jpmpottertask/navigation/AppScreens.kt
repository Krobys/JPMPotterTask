package exomind.online.jpmpottertask.navigation

import android.R.attr.type
import androidx.navigation.NavType
import androidx.navigation.navArgument
import kotlinx.serialization.Serializable

sealed interface AppScreens {

    @Serializable
    object CharacterList : AppScreens

    @Serializable
    data class CharacterDetails(val id: String) : AppScreens

}
