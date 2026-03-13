package com.example.smartalarmsapp.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val AppDarkColorScheme = darkColorScheme(
    primary          = AppPrimary,
    onPrimary        = Color.White,
    secondary        = AppGreen,
    onSecondary      = Color.White,
    background       = AppBackground,
    onBackground     = AppText,
    surface          = AppSurface,
    onSurface        = AppText,
    surfaceVariant   = AppSurfaceVariant,
    onSurfaceVariant = AppTextSecondary,
    outline          = AppDivider,
    error            = AppRed,
    onError          = Color.White
)

@Composable
fun SmartAlarmsAppTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = AppDarkColorScheme,
        typography  = Typography,
        content     = content
    )
}