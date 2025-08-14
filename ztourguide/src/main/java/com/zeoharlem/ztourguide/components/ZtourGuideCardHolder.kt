package com.zeoharlem.ztourguide.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults.cardColors
import androidx.compose.material3.CardDefaults.cardElevation
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.LayoutCoordinates
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import com.zeoharlem.ztourguide.models.CustomTriangle
import com.zeoharlem.ztourguide.models.Direction
import com.zeoharlem.ztourguide.models.DisplayProperty
import com.zeoharlem.ztourguide.models.ZtourGuideConfig

@Composable
internal fun ZTourGuideShowCardHolder(
    displayProperty: DisplayProperty,
    targetRect: Rect,
    targetRadius: Float,
    displayAlignment: Alignment.Vertical,
    countIndicator: @Composable (()-> Unit)? = null,
    updateCoordinates: ((LayoutCoordinates) -> Unit)? = null,
    onDismissEvent: (() -> Unit)? = null,
    ztourGuideConfig: ZtourGuideConfig = ZtourGuideConfig(),
    onClickPreviousBtn: () -> Unit,
    onClickNextBtn: () -> Unit,
    onResetEvent: () -> Unit
) {

    ZTourGuideCardHolderContent(
        displayProperty = displayProperty,
        targetRect = targetRect,
        targetRadius = targetRadius,
        displayAlignment = displayAlignment,
        countIndicator = countIndicator,
        updateCoordinates = updateCoordinates,
        onDismissEvent = onDismissEvent,
        ztourGuideConfig = ztourGuideConfig,
        onResetEvent = onResetEvent,
        onClickPreviousBtn = onClickPreviousBtn,
        onClickNextBtn = onClickNextBtn
    )
}

@Composable
private fun ZTourGuideCardHolderContent(
    displayProperty: DisplayProperty,
    targetRect: Rect,
    targetRadius: Float,
    displayAlignment: Alignment.Vertical,
    countIndicator: @Composable (()-> Unit)? = null,
    updateCoordinates: ((LayoutCoordinates) -> Unit)? = null,
    ztourGuideConfig: ZtourGuideConfig = ZtourGuideConfig(),
    onDismissEvent: (() -> Unit)? = null,
    onClickPreviousBtn: (() -> Unit)? = null,
    onResetEvent: () -> Unit,
    onClickNextBtn: () -> Unit
) {
    val mDensity = LocalDensity.current
    var offsetY by remember { mutableFloatStateOf(0f) }

    val getArrowAlignment by remember(displayAlignment) {
        derivedStateOf {
            when (displayAlignment) {
                Alignment.Top -> Direction.BOTTOM
                else -> Direction.TOP
            }
        }
    }

    val altDisplayProperty = displayProperty.copy(
        offsetPadding = when (displayAlignment) {
            Alignment.Top -> 50.dp
            else -> 0.dp
        },
        arrowDirection = getArrowAlignment
    )

    val targetItemIsAtStart = targetRect.topLeft.x <= 0
    //val targetItemIsAtEnd = (targetRect.topRight.x + targetRect.width) >= screenWidth * 0.9f

    val targetAdjustment by remember(targetRect) {
        derivedStateOf {
            when {
                targetItemIsAtStart -> with(mDensity) {
                    targetRect.center.x.toDp()
                }
                else -> with(mDensity) {
                    targetRect.center.x.toDp() - 15.dp
                }
            }
        }
    }

    val cardBgColor = ztourGuideConfig.cardColors?.invoke()?.containerColor ?: Color.White

    Column(
        modifier = Modifier
            .wrapContentSize()
            .onGloballyPositioned {
                updateCoordinates?.invoke(it)
                val componentHeight = it.size.height
                val possibleTop = targetRect.center.y - componentHeight

                offsetY = when {
                    (possibleTop > 0) -> possibleTop - altDisplayProperty.arrowPaddingToTargetItem
                    else -> targetRect.center.y + targetRadius + altDisplayProperty.arrowPaddingToTargetItem
                }
            }
            .offset(y = with(mDensity) {
                offsetY.toDp().minus(altDisplayProperty.offsetPadding)
            })
    ) {

        /* ---- Tour guide card content ----*/
        Row(
            modifier = Modifier
                .wrapContentSize()
                .zIndex(2f)
                .alpha(if (altDisplayProperty.pointerDisplayTop) 1f else 0f)
                .offset(x = targetAdjustment)
        ) {
            ArrowPointer(altDisplayProperty.arrowDirection, cardBgColor)
        }

        Card(
            elevation = cardElevation(5.dp),
            shape = RoundedCornerShape(15.dp),

            colors = ztourGuideConfig.cardColors?.invoke()
                ?: cardColors(containerColor = Color.White),

            modifier = Modifier
                .fillMaxWidth()
                .border(
                    width = 2.dp,
                    color = Color.Transparent,
                    shape = RoundedCornerShape(15.dp)
                )
                .padding(horizontal = 10.dp)
        ) {
            Box(modifier = Modifier.padding(15.dp)) {
                Icon(
                    contentDescription = "close",
                    imageVector = ztourGuideConfig.tourGuideCloseIcon,
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .clickable { onDismissEvent?.invoke() }
                )

                Column(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(5.dp)
                ) {

                    ztourGuideConfig.titleText?.invoke(displayProperty.title)
                        ?: Text(displayProperty.title)

                    ztourGuideConfig.descriptionText?.invoke(displayProperty.subTitle)
                        ?: Text(displayProperty.subTitle)

                    Row(
                        horizontalArrangement = Arrangement.spacedBy(10.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        if(ztourGuideConfig.showPrevBtn) {
                            ztourGuideConfig.prevBtn?.invoke() ?: Button(onClick = { onClickPreviousBtn?.invoke() }) {
                                Text(text = ztourGuideConfig.prevBtnText)
                            }
                        }

                        if(ztourGuideConfig.showNextBtn) {
                            ztourGuideConfig.nextBtn?.invoke() ?: Button(onClick = onClickNextBtn) {
                                Text(text = ztourGuideConfig.nextBtnText)
                            }
                        }

                        if(ztourGuideConfig.showResetBtn) {
                            ztourGuideConfig.resetBtn?.invoke() ?: Button(onClick = onResetEvent) {
                                Text(text = ztourGuideConfig.resetBtnText)
                            }
                        }

                        countIndicator?.let { countInfo ->
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .weight(1f),
                                horizontalArrangement = Arrangement.Absolute.Right,
                            ) {
                                countInfo.invoke()
                            }
                        }
                    }
                }
            }
        }

        Row(
            modifier = Modifier
                .wrapContentSize()
                .zIndex(2f)
                .alpha(if (altDisplayProperty.pointerDisplayTop) 0f else 1f)
                .offset(x = targetAdjustment, y = -(2.dp))
        ) {
            ArrowPointer(altDisplayProperty.arrowDirection, cardBgColor)
        }
    }
}

@Composable
private fun ArrowPointer(direction: Direction, color: Color = Color.White) {
    Box(
        modifier = Modifier
            .width(20.dp)
            .height(18.dp)
            .clip(shape = CustomTriangle(direction))
            .background(color)
    )
}

@Composable
internal fun rememberScreenSizePx(): Pair<Float, Float> {
    val configuration = LocalConfiguration.current
    return remember(configuration) {
        val widthPx = configuration.screenWidthDp
        val heightPx = configuration.screenHeightDp
        widthPx.toFloat() to heightPx.toFloat()
    }
}