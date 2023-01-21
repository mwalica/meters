package ch.walica.meters.domain.model

import androidx.compose.ui.graphics.Color
import ch.walica.meters.ui.theme.Red

data class MeterCard(
    val name: String,
    val color: Color = Red,
    val route: String,
    val avg: Int = 0,
    val img: Int,
)
