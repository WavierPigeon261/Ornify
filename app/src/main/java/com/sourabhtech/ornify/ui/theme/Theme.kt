package com.sourabhtech.ornify.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext

private val DarkColorScheme = darkColorScheme(
    primary = GoldLight,
    onPrimary = Color.Black,
    primaryContainer = GoldDark,
    onPrimaryContainer = GoldLight,
    secondary = GoldPrimary,
    onSecondary = Color.Black,
    background = Color(0xFF141312),
    surface = Color(0xFF1E1C1A),
    onBackground = Color(0xFFEDE0D4),
    onSurface = Color(0xFFEDE0D4)
)

private val LightColorScheme = lightColorScheme(
    primary = GoldDark,
    onPrimary = Color.White,
    primaryContainer = GoldAccentContainer,
    onPrimaryContainer = Color(0xFF422E00),
    secondary = GoldWarm,
    onSecondary = Color.White,
    background = WarmBackground,
    surface = SurfaceCard,
    onBackground = TextPrimary,
    onSurface = TextPrimary
)

/**
 * Ornify App Theme supporting Material You (Dynamic Color) on Android 12+ (API 31+).
 * Automatically adapts to the phone's wallpaper and system color palette.
 */
@Composable
fun OrnifyTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }
        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
