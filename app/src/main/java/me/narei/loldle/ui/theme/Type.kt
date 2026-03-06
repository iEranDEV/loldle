package me.narei.loldle.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import me.narei.loldle.R


val BeaufortFontFamily = FontFamily(
    Font(R.font.beaufortforlol_heavy, FontWeight.ExtraBold),
    Font(R.font.beaufortforlol_bold, FontWeight.Bold),
    Font(R.font.beaufortforlol_medium, FontWeight.Medium),
    Font(R.font.beaufortforlol_regular, FontWeight.Normal),
    Font(R.font.beaufortforlol_light, FontWeight.Light),
)

val Typography = Typography(

    titleLarge = TextStyle(
        fontFamily = BeaufortFontFamily,
        fontSize = 24.sp,
        fontWeight = FontWeight.Bold,
        letterSpacing = 0.5.sp,
    ),
    bodySmall = TextStyle(
        fontSize = 12.sp,
        fontWeight = FontWeight.Normal,
    )


)