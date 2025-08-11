package com.zeoharlem.ztourguide.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.CompositingStrategy
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import com.zeoharlem.ztourguide.ZtourGuidePlayManager
import com.zeoharlem.ztourguide.models.DisplayProperty
import com.zeoharlem.ztourguide.models.ZtourGuideConfig

@Composable
fun ZtourGuideOverlayPlayer(
    manager: ZtourGuidePlayManager,
    targetCoordinates: Rect?,
    ztourGuideConfig: ZtourGuideConfig = ZtourGuideConfig(),
    onDismiss: () -> Unit = {},
    onFinish: () -> Unit
) {
    val mContext = LocalContext.current
    val mDensity = LocalDensity.current

    val targetRadius = targetCoordinates?.maxDimension?.div(2f)?.plus(20f)

    val heightOfScreenInPx = with(mDensity) {
        mContext.resources.displayMetrics.heightPixels.toDp()
    }

    //val displayOnTop = (targetCoordinates?.bottom?.plus(100f) ?: 0f) < heightOfScreenInPx
    //val displayBelow = targetCoordinates != null && (targetCoordinates.bottom.dp < heightOfScreenInPx / 2)
    val displayOnTop =
        targetCoordinates != null && (targetCoordinates.top.dp > heightOfScreenInPx / 2)

    val displayAlignment by remember(displayOnTop) {
        derivedStateOf {
            if (displayOnTop) Alignment.Top else Alignment.Bottom
        }
    }

    val useShape = manager.currentTourStep?.displayProperty?.displayShape!!

    if (targetCoordinates != null && manager.currentTourStep != null) {

        Box(
            modifier = Modifier
                .fillMaxSize()
                .graphicsLayer { compositingStrategy = CompositingStrategy.Offscreen }
        ) {
            // Highlight target
            Canvas(modifier = Modifier.fillMaxSize()) {
                drawRect(color = ztourGuideConfig.overlayColor)

                when (useShape) {
                    DisplayProperty.UsedShape.RECTANGLE -> drawRoundRect(
                        color = Color.Transparent,
                        topLeft = targetCoordinates.topLeft,
                        cornerRadius = CornerRadius(x = 15f, y = 15f),
                        size = Size(targetCoordinates.width, targetCoordinates.height),
                        blendMode = BlendMode.Clear,
                        style = Fill
                    )

                    DisplayProperty.UsedShape.SQUARE -> TODO()
                    DisplayProperty.UsedShape.CIRCLE -> {
                        drawCircle(
                            color = ztourGuideConfig.highlightColor,
                            radius = (targetRadius ?: 0f) / 1.2f,
                            center = targetCoordinates.center,
                            blendMode = BlendMode.Clear,
                            style = Fill
                        )
                    }
                }
            }

            ZTourGuideShowCardHolder(
                displayProperty = manager.currentTourStep?.displayProperty!!,
                targetRect = targetCoordinates,
                targetRadius = targetRadius ?: 0f,
                displayAlignment = displayAlignment,
                ztourGuideConfig = ztourGuideConfig.copy(
                    nextBtnText = if (manager.isLastStep()) "Finish" else "Next"
                ),
                onResetEvent = { manager.reset() },
                onDismissEvent = onDismiss,
                onClickPreviousBtn = { manager.previousStep() },
                onClickNextBtn = {
                    if (manager.isLastStep()) onFinish()
                    else manager.nextStep()
                }
            )
        }
    }
}