package exomind.online.jpmpottertask.presentation.characters.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import exomind.online.jpmpottertask.ui.theme.houseColors

@Composable
fun HouseChip(
    modifier: Modifier = Modifier,
    houseName: String,
) {
    Surface(
        modifier = modifier,
        color = houseName.toHouseColor(),
        shape = MaterialTheme.shapes.large
    ) {
        Text(
            text = houseName,
            modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
            color = Color.White,
            style = MaterialTheme.typography.bodyMedium
        )
    }
}

@Preview
@Composable
fun HouseChipPreview() {
    Column(
        modifier = Modifier
            .padding(16.dp)
            .background(Color.White)
    ) {
        HouseChip(houseName = "Gryffindor")
        Spacer(modifier = Modifier.height(8.dp))
        HouseChip(houseName = "Slytherin")
        Spacer(modifier = Modifier.height(8.dp))
        HouseChip(houseName = "Ravenclaw")
        Spacer(modifier = Modifier.height(8.dp))
        HouseChip(houseName = "Hufflepuff")
        Spacer(modifier = Modifier.height(8.dp))
        HouseChip(houseName = "Unknown")
    }
}

@Composable
internal fun String.toHouseColor(): Color {
    return when (lowercase()) {
        "gryffindor" -> MaterialTheme.houseColors.griffindor
        "slytherin" -> MaterialTheme.houseColors.slytherin
        "ravenclaw" -> MaterialTheme.houseColors.ravenclaw
        "hufflepuff" -> MaterialTheme.houseColors.hufflepuff
        else -> Color.Gray
    }
}
