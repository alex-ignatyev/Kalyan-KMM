package ui.themes

import androidx.compose.ui.graphics.Color

internal val baseLightPalette = KalyanColors(
    primaryBackground = Color(0xFFFAFAFA),
    secondaryBackground = Color(0xFFEEEEEE),

    generalColor = Color(0xFFB3E5FC),

    primaryText = Color(0xFF3D454C),
    secondaryText = Color(0xCC7A8A99),
    tintColor = Color.Magenta,
    controlColor = Color(0xFF7A8A99),
    errorColor = Color(0xFFFF3377)
)

internal val baseDarkPalette = KalyanColors(
    primaryBackground = Color(0xFF0B0C10),
    secondaryBackground = Color(0xFF303643),

    generalColor = Color(0xFF199FE2),

    primaryText = Color(0xFFF2F4F5),
    secondaryText = Color(0xCC7A8A99),
    tintColor = Color.Magenta,
    controlColor = Color(0xFF7A8A99),
    errorColor = Color(0xFFFF6699)
)

val purpleLightPalette = baseLightPalette.copy(
    tintColor = Color(0xFFAD57D9)
)

val purpleDarkPalette = baseDarkPalette.copy(
    tintColor = Color(0xFFD580FF)
)

val orangeLightPalette = baseLightPalette.copy(
    tintColor = Color(0xFFFF6619)
)

val orangeDarkPalette = baseDarkPalette.copy(
    tintColor = Color(0xFFFF974D)
)

val blueLightPalette = baseLightPalette.copy(
    tintColor = Color(0xFF4D88FF)
)

val blueDarkPalette = baseDarkPalette.copy(
    tintColor = Color(0xFF99BBFF)
)

val redLightPalette = baseLightPalette.copy(
    tintColor = Color(0xFFE63956)
)

val redDarkPalette = baseDarkPalette.copy(
    tintColor = Color(0xFFFF5975)
)

val greenLightPalette = baseLightPalette.copy(
    tintColor = Color(0xFF12B37D)
)

val greenDarkPalette = baseDarkPalette.copy(
    tintColor = Color(0xFF7EE6C3)
)