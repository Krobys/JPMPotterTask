package exomind.online.jpmpottertask.presentation.characters.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import exomind.online.jpmpottertask.domain.models.Character
import exomind.online.jpmpottertask.presentation.characters.CharactersListViewModel
import exomind.online.jpmpottertask.presentation.characters.model.Effect
import exomind.online.jpmpottertask.presentation.characters.model.Event
import exomind.online.jpmpottertask.presentation.characters.model.UIState
import exomind.online.jpmpottertask.ui.theme.AppTheme

@Composable
fun CharactersListScreen(
    viewModel: CharactersListViewModel = hiltViewModel<CharactersListViewModel>(),
    navigateCharacter: ((id: String) -> Unit),
) {
    val state = viewModel.characters.collectAsStateWithLifecycle()
    val query = viewModel.query.collectAsStateWithLifecycle()
    val listState = rememberLazyListState()

    LaunchedEffect(Unit) {
        viewModel.effects.collect { effect ->
            when (effect) {
                is Effect.NavigateToCharacterDetails -> {
                    navigateCharacter(effect.character.id)
                }
                Effect.ScrollToTop -> {
                    listState.animateScrollToItem(0)
                }
            }
        }
    }

    CharactersListUI(
        state = state.value,
        onEvent = viewModel::onEvent,
        query = query,
        listState = listState
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CharactersListUI(
    state: UIState,
    onEvent: (Event) -> Unit,
    query: State<String>,
    listState: LazyListState
) {
    val showScrollToTop by remember(state, listState) {
        derivedStateOf {
            listState.firstVisibleItemIndex > 0 && state is UIState.Success
        }
    }

    Scaffold(
        floatingActionButton = {
            if (showScrollToTop) {
                ScrollToTopButton(
                    onClick =  {
                        onEvent(Event.ScrollToTop)
                    }
                )
            }
        }
    ) { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
        ) {
            when (state) {
                is UIState.Loading -> LoadingView()

                is UIState.Error -> ErrorView(
                    message = state.message,
                    onRetry = {
                        onEvent(Event.RetryCharacters)
                    }
                )

                is UIState.Success -> SuccessView(
                    characters = state.characters,
                    listState = listState,
                    queryState = query,
                    contentPadding = padding,
                    onEvent = onEvent
                )
            }
        }
    }
}

@Composable
private fun LoadingView() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator()
    }
}

@Composable
private fun ErrorView(
    message: String,
    onRetry: (() -> Unit),
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = message,
            textAlign = TextAlign.Center
        )
        Spacer(Modifier.height(16.dp))
        FilledTonalButton(
            onClick = { onRetry() },
        ) {
            Text(
                text = "Try again"
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun SuccessView(
    characters: List<Character>,
    listState: LazyListState,
    queryState: State<String>,
    contentPadding: PaddingValues,
    onEvent: (Event) -> Unit,
) {
    Column {
        SearchBarSection(
            query = queryState.value,
            onQueryChange = {
                onEvent(Event.QueryCharacters(it))
            }
        )
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp),
            state = listState,
            contentPadding = PaddingValues(
                top = 8.dp,
                bottom = contentPadding.calculateBottomPadding(),
            ),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            if (queryState.value.isNotBlank() && characters.isEmpty()) {
                item { EmptyResultView() }
            }
            items(characters) { character ->
                CharacterItem(
                    character = character,
                    onClick = {
                        onEvent(Event.NavigateCharacterDetails(character))
                    }
                )
            }
        }
    }
}

@Composable
private fun EmptyResultView() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        Text("No results found")
    }
}

@Preview(showSystemUi = true)
@Composable
fun CharactersListPreview() {
    AppTheme {
        CharactersListUI(
            state = UIState.Success(
                buildList {
                    repeat(4) {
                        this.add(
                            Character(
                                id = "id",
                                characterName = "Character Name",
                                actorName = "Actor name",
                                imageUrl = "url",
                                species = "Human",
                                house = "gryffindor",
                                dateOfBirth = null,
                                alive = true
                            )
                        )
                    }
                }
            ),
            query = remember { mutableStateOf("") },
            onEvent = {},
            listState = rememberLazyListState()
        )
    }
}