package com.thariqzs.wanderai.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.ExperimentalTextApi
import androidx.compose.ui.text.PlatformTextStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.thariqzs.wanderai.R

val dmSansRegular = FontFamily(Font(R.font.dm_sans_regular))
val dmSansBold = FontFamily(Font(R.font.dm_sans_bold))
val plusJakartaBold = FontFamily(Font(R.font.plus_jakarta_bold))

// Set of Material typography styles to start with
val Typography = Typography(
    bodyLarge = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.5.sp
    ),
    /* Other default text styles to override
    titleLarge = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 22.sp,
        lineHeight = 28.sp,
        letterSpacing = 0.sp
    ),
    labelSmall = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Medium,
        fontSize = 11.sp,
        lineHeight = 16.sp,
        letterSpacing = 0.5.sp
    )
    */
)
val h1 = TextStyle(
    fontFamily = plusJakartaBold,
    fontWeight = FontWeight.Bold,
    fontSize = 52.sp,
    platformStyle = PlatformTextStyle(
        includeFontPadding = false
    )
)
val h2 = TextStyle(
    fontFamily = plusJakartaBold,
    fontWeight = FontWeight.Bold,
    fontSize = 40.sp,
    platformStyle = PlatformTextStyle(
        includeFontPadding = false
    )
)
val h3 = TextStyle(
    fontFamily = plusJakartaBold,
    fontWeight = FontWeight.Bold,
    fontSize = 28.sp,
    platformStyle = PlatformTextStyle(
        includeFontPadding = false
    )

)
val h4 = TextStyle(
    fontFamily = plusJakartaBold,
    fontWeight = FontWeight.Bold,
    fontSize = 20.sp,
    platformStyle = PlatformTextStyle(
        includeFontPadding = false
    )
)
val sh1 = TextStyle(
    fontFamily = dmSansBold,
    fontWeight = FontWeight.Bold,
    fontSize = 24.sp,
)
val sh2 = TextStyle(
    fontFamily = dmSansBold, fontWeight = FontWeight.Bold, fontSize = 20.sp
)
val b1 = TextStyle(
    fontFamily = dmSansRegular, fontWeight = FontWeight.Normal, fontSize = 18.sp
)
val b2 = TextStyle(
    fontFamily = dmSansRegular, fontWeight = FontWeight.Normal, fontSize = 16.sp
)