package exomind.online.jpmpottertask.presentation.characters

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import exomind.online.jpmpottertask.domain.Character
import exomind.online.jpmpottertask.ui.theme.HouseColors
import exomind.online.jpmpottertask.ui.theme.LocalHouseColors

@Composable
fun CharacterItem(
    character: Character,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth(),
        onClick = onClick
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = character.characterName,
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onSurface,
                    modifier = Modifier.weight(1f)
                )
                Spacer(modifier = Modifier.width(8.dp))
                HouseChip(houseName = character.house)
            }
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Actor: ${character.actorName}",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurface
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "Species: ${character.species}",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurface
            )
        }
    }
}

@Composable
@Preview(showBackground = true)
fun CharacterItemPreview() {
    CompositionLocalProvider(LocalHouseColors provides HouseColors()) {
        CharacterItem(
            character = Character(
                id = "",
                characterName = "Harry Potter",
                actorName = "Daniel Radcliffe",
                imageUrl = "",
                species = "Wizard",
                house = "Gryffindor",
                alive = true
            ),
            onClick = {}
        )
    }
}
