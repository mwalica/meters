package ch.walica.meters.ui.theme

import androidx.compose.material.Text
import androidx.compose.material.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

// Set of Material typography styles to start with
val Typography = Typography(
    body1 = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp
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
        color = DarkGrey,
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