package com.company.attendanceapp.presentation.components.common

import androidx.annotation.RawRes
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition

@Composable
fun AppLottieAnimation(
    @RawRes resId: Int,
    modifier: Modifier = Modifier,
    size: Dp = 200.dp,
    iterations: Int = LottieConstants.IterateForever,
    shouldAnimate: Boolean = true
) {
    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(resId))
    val progress by animateLottieCompositionAsState(
        composition = composition,
        iterations = iterations,
        isPlaying = shouldAnimate
    )

    LottieAnimation(
        composition = composition,
        progress = { progress },
        modifier = modifier.size(size)
    )
}
