package exomind.online.jpmpottertask.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import exomind.online.jpmpottertask.navigation.AppScreen.CharacterDetails
import exomind.online.jpmpottertask.navigation.AppScreen.CharacterList
import exomind.online.jpmpottertask.presentation.characters.ui.CharactersListScreen
import exomind.online.jpmpottertask.presentation.details.ui.CharacterDetailsScreen

@Composable
fun AppNavHost() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = CharacterList,
    ) {
        composable<CharacterList> {
            CharactersListScreen(
                navigateCharacter = { characterID ->
                    navController.navigate(CharacterDetails(characterID))
                }
            )
        }

        composable<CharacterDetails> { backStackEntry ->
            val id: String = backStackEntry.toRoute<CharacterDetails>().id
            CharacterDetailsScreen(
                characterId = id,
                onNavigateBack = {
                    navController.popBackStack()
                }
            )
        }
    }
}
