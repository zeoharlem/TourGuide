package com.zeoharlem.ztourguide.models

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.CardColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector

/**
 * @property targetKey The key of the target component to be highlighted.
 * @property displayProperty The object containing the property to be displayed for the target component.
 * @property onNextEvent The event to be triggered when the next button is clicked.
 * @sample com.zeoharlem.ztourguide.samples.CreateZtourGuideStepConfigExample
 */
data class ZTourGuideStep(
    val targetKey: String,
    val displayProperty: DisplayProperty? = null,
    val onNextEvent: (() -> Unit)? = null
)

/**
 * @property showNextBtn Show/Hide the next button by default it is true
 * @property showPrevBtn Show/Hide the previous button by default it is true
 * @property showResetBtn Show/Hide the reset button by default it is true
 * @property prevBtn Composable previous button function
 * @property nextBtn Composable next button function
 * @property resetBtn Composable reset button function
 * @property titleText Composable title text function
 * @property descriptionText Composable description text function
 * @property tourGuideCloseIcon ImageVector
 * @property overlayColor Color
 * @property cardColors e.g CardColors.color(color = Color.White)
 * @property highlightColor color
 * @sample com.zeoharlem.ztourguide.samples.CreateZtourGuideConfigExample
 */
data class ZtourGuideConfig(
    val showPrevBtn: Boolean = true,
    val showNextBtn: Boolean = true,
    val showResetBtn: Boolean = true,
    val prevBtn: @Composable (() -> Unit)? = null,
    val nextBtn: @Composable (() -> Unit)? = null,
    val resetBtn: @Composable (() -> Unit)? = null,
    val prevBtnText: String = "Prev",
    val nextBtnText: String = "Next",
    val resetBtnText: String = "Restart",
    val titleText: @Composable ((text: String) -> Unit)? = null,
    val descriptionText: @Composable ((text: String) -> Unit)? = null,
    val tourGuideCloseIcon: ImageVector = Icons.Default.Close,
    val overlayColor: Color = Color.Black.copy(alpha = 0.8f),
    val cardColors: @Composable (() -> CardColors)? = null,
    val highlightColor: Color = Color.Transparent,
)

