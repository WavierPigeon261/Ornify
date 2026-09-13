package com.jewellery.shoporders.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

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

@Composable
fun JewelleryShopOrdersTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
