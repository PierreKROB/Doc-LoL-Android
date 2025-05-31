package com.pkrob.ApiDocLoL.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val DarkColorPalette = darkColorScheme(
    primary = BluePrimary,
    onPrimary = TextPrimaryDark,
    primaryContainer = BlueAccent,
    onPrimaryContainer = BackgroundDark,
    
    secondary = GoldPrimary,
    onSecondary = BackgroundDark,
    secondaryContainer = GoldAccent,
    onSecondaryContainer = BackgroundDark,
    
    tertiary = SilverAccent,
    onTertiary = BackgroundDark,
    
    background = BackgroundDark,
    onBackground = TextPrimaryDark,
    
    surface = SurfaceDark,
    onSurface = TextPrimaryDark,
    surfaceVariant = SurfaceVariant,
    onSurfaceVariant = TextSecondaryDark,
    
    outline = TextSecondaryDark,
    outlineVariant = SurfaceVariant,
    
    error = ErrorColor,
    onError = TextPrimaryDark
)

private val LightColorPalette = lightColorScheme(
    primary = BluePrimary,
    onPrimary = Color.White,
    primaryContainer = BlueAccent,
    onPrimaryContainer = BackgroundDark,
    
    secondary = GoldPrimary,
    onSecondary = Color.White,
    secondaryContainer = GoldAccent,
    onSecondaryContainer = BackgroundDark,
    
    tertiary = SilverAccent,
    onTertiary = BackgroundDark,
    
    background = Color.White,
    onBackground = TextPrimaryLight,
    
    surface = Color.White,
    onSurface = TextPrimaryLight,
    surfaceVariant = Color(0xFFF5F5F5),
    onSurfaceVariant = TextSecondaryLight,
    
    outline = TextSecondaryLight,
    outlineVariant = Color(0xFFE0E0E0),
    
    error = ErrorColor,
    onError = Color.White
)

@Composable
fun ApiDocLoLTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colors = if (darkTheme) {
        DarkColorPalette
    } else {
        LightColorPalette
    }

    val typography = if (darkTheme) {
        DarkTypography
    } else {
        LightTypography
    }

    MaterialTheme(
        colorScheme = colors,
        typography = typography,
        content = content
    )
}
