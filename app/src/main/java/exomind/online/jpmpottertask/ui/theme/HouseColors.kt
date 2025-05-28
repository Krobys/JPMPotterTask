package exomind.online.jpmpottertask.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color

class HouseColors(
    val griffindor: Color = Color(0xFF740001),
    val slytherin: Color = Color(0xFF1a472a),
    val ravenclaw: Color = Color(0xFF0c1a40),
    val hufflepuff: Color = Color(0xFFeeb939)
)

internal val LocalHouseColors = staticCompositionLocalOf { HouseColors() }

val MaterialTheme.houseColors: HouseColors
    @Composable
    get() = LocalHouseColors.current