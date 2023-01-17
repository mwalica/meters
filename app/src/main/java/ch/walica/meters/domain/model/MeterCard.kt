package ch.walica.meters.domain.model

data class MeterCard(
    val name: String,
    val route: String,
    val avg: Int = 0,
    val img: Int
)
