package com.example.ui.theme

import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color

// Primary Accents
val PinkAccent = Color(0xFFFA5E7C)           // Floating action arrow & active nav tint
val PinkAccentLight = Color(0xFFFF8FA2)      // Arrow gradient highlight
val HeartRed = Color(0xFFFF5271)             // Favorite / heart icon
val NotificationDot = Color(0xFFE53935)      // Bell alert dot

// Backgrounds & Surfaces
val ScreenBackground = Color(0xFFFBE4E7)     // Main screen soft pink background
val CardBackground = Color(0xFFFDD4DC)       // Product card container tint
val SurfaceWhite = Color(0xFFFFFFFF)         // Action circles, search bar, nav items
val FrostedGlass = Color(0x80FFFFFF)         // Blur overlay container (50% white)

// Typography & Icons
val TextPrimary = Color(0xFF1F1F1F)          // Headers, title, and price
val TextSecondary = Color(0xFF757575)        // Subtitles ("Good Morning", product subtext)
val TextPlaceholder = Color(0xFF9E9E9E)      // "Search" hint
val IconTint = Color(0xFF262626)             // Cart, bell, and inactive bottom bar icons

// Gradients
val AccentButtonGradient = Brush.linearGradient(
    colors = listOf(PinkAccentLight, PinkAccent)
)

val ActiveNavGlowGradient = Brush.radialGradient(
    colors = listOf(
        PinkAccent.copy(alpha = 0.45f),
        Color.Transparent
    )
)