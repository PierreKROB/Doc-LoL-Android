package com.pkrob.ApiDocLoL.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.pkrob.ApiDocLoL.R

val LeagueFont = FontFamily(
    Font(R.font.league_of_legends_font, FontWeight.Normal)
)

val LightTypography = Typography(
    displayLarge = TextStyle(
        fontFamily = LeagueFont,
        fontWeight = FontWeight.Bold,
        fontSize = 30.sp,
        color = TextPrimaryLight
    ),
    displayMedium = TextStyle(
        fontFamily = LeagueFont,
        fontWeight = FontWeight.Bold,
        fontSize = 24.sp,
        color = TextPrimaryLight
    ),
    bodyLarge = TextStyle(
        fontFamily = LeagueFont,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        color = TextPrimaryLight
    ),
    bodyMedium = TextStyle(
        fontFamily = LeagueFont,
        fontWeight = FontWeight.Normal,
        fontSize = 14.sp,
        color = TextSecondaryLight
    )
)

val DarkTypography = Typography(
    displayLarge = TextStyle(
        fontFamily = LeagueFont,
        fontWeight = FontWeight.Bold,
        fontSize = 30.sp,
        color = TextPrimaryDark
    ),
    displayMedium = TextStyle(
        fontFamily = LeagueFont,
        fontWeight = FontWeight.Bold,
        fontSize = 24.sp,
        color = TextPrimaryDark
    ),
    bodyLarge = TextStyle(
        fontFamily = LeagueFont,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        color = TextPrimaryDark
    ),
    bodyMedium = TextStyle(
        fontFamily = LeagueFont,
        fontWeight = FontWeight.Normal,
        fontSize = 14.sp,
        color = TextSecondaryDark
    )
)
