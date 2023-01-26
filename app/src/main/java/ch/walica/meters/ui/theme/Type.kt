package ch.walica.meters.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import ch.walica.meters.R


val Urbanist = FontFamily(
    Font(R.font.urbanist_light, weight = FontWeight.Light),
    Font(R.font.urbanist_regular, weight = FontWeight.Normal),
    Font(R.font.urbanist_bold, weight = FontWeight.Bold)
)

val Lato = FontFamily(
    Font(R.font.lato_light, weight = FontWeight.Light),
    Font(R.font.lato_regular, weight = FontWeight.Normal),
    Font(R.font.lato_bold, weight = FontWeight.Bold)
)

val Roboto = FontFamily(
    Font(R.font.roboto_light, weight = FontWeight.Light),
    Font(R.font.roboto_regular, weight = FontWeight.Normal),
    Font(R.font.roboto_bold, weight = FontWeight.Bold)
)

// Set of Material typography styles to start with
val Typography = Typography(
    defaultFontFamily = Roboto,
    body1 = TextStyle(
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
    ),
    subtitle1 = TextStyle(
        color = DarkGrey,
        fontSize = 14.sp
    ),
    subtitle2 = TextStyle(
        color = Grey,
        fontSize = 12.sp
    ),
    h5 = TextStyle(
        color = DarkGrey,
        fontSize = 14.sp,
        fontWeight = FontWeight.Bold
    ),
    button = TextStyle(
        fontSize = 14.sp,
    )
    /* Other default text styles to override
    button = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.W500,
        fontSize = 14.sp
    ),
    caption = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 12.sp
    )
    */
)