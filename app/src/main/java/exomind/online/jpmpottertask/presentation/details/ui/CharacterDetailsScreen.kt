package exomind.online.jpmpottertask.presentation.details.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import exomind.online.jpmpottertask.domain.models.Character
import exomind.online.jpmpottertask.presentation.details.CharacterDetailsViewModel
import exomind.online.jpmpottertask.presentation.details.model.Effect
import exomind.online.jpmpottertask.presentation.details.model.Event
import exomind.online.jpmpottertask.presentation.details.model.UIState
import exomind.online.jpmpottertask.ui.theme.HouseColors
import exomind.online.jpmpottertask.ui.theme.LocalHouseColors

@Composable
fun CharacterDetailsScreen(
    characterId: String,
    viewModel: CharacterDetailsViewModel = hiltViewModel(),
    onNavigateBack: (() -> Unit),
) {
    LaunchedEffect(Unit) {
        viewModel.requestCharacter(characterId)
    }

    LaunchedEffect(Unit) {
        viewModel.effects.collect { effect ->
            when (effect) {
                is Effect.NavigateBack -> onNavigateBack()
            }
        }
    }

    val state by viewModel.character.collectAsState()
    CharacterDetailsUI(
        modifier = Modifier,
        state = state,
        onEvent = viewModel::onEvent
    )
}

@Composable
private fun CharacterDetailsUI(
    modifier: Modifier = Modifier,
    state: UIState,
    onEvent: (Event) -> Unit,
) {
    when (state) {
        UIState.Loading -> LoadingContent(modifier)
        is UIState.Error -> ErrorContent(state.message, modifier)
        is UIState.Success -> SuccessContent(
            modifier = modifier,
            character = state.character,
            onBack = { onEvent(Event.NavigateBack) },
        )
    }
}

@Composable
fun LoadingContent(modifier: Modifier = Modifier) {
    Box(modifier.fillMaxSize()) {
        CircularProgressIndicator(Modifier.align(Alignment.Center))
    }
}

@Composable
fun ErrorContent(message: String, modifier: Modifier = Modifier) {
    Box(modifier.fillMaxSize()) {
        Text(text = "Error: $message", Modifier.align(Alignment.Center))
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SuccessContent(
    modifier: Modifier = Modifier,
    character: Character,
    onBack: () -> Unit,
) {
    Scaffold(
        topBar = { DetailsTopBar(name = character.characterName, onBack = onBack) },
        modifier = modifier
    ) { padding ->
        Column(
            Modifier
                .padding(padding)
                .verticalScroll(rememberScrollState())
        ) {
            BannerSection(
                name = character.characterName,
                imageUrl = character.imageUrl,
                house = character.house
            )
            Spacer(Modifier.height(24.dp))
            InfoCard(label = "Actor", value = character.actorName)
            InfoCard(label = "Species", value = character.species)
            InfoCard(label = "Status", value = if (character.alive) "Alive" else "Dead")
            InfoCard(label = "Date of Birth", value = character.dateOfBirth ?: "Unknown")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CharacterDetailsPreview() {
    CompositionLocalProvider(LocalHouseColors provides HouseColors()) {
        SuccessContent(
            character = Character(
                id = "1",
                characterName = "Hermione Granger",
                actorName = "Emma Watson",
                imageUrl = "",
                species = "Human",
                house = "Ravenclaw",
                dateOfBirth = "19 Sep 1979",
                alive = true
            ),
            onBack = {}
        )
    }
}
