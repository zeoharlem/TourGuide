package com.zeoharlem.ztourguide.models

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp

/**
 * @property title The title of the step
 * @property subTitle The description of the step
 * @property offsetPadding
 * @property titleColor The color of the title
 * @property subTitleColor The color of the description
 * @property displayShape The shape of used to cut out the target item
 * @property arrowDirection The direction of the arrow(up/down
 * @property arrowPaddingToTargetItem The padding between the arrow and the target item
 * @property outerSpace
 * @property blurOpacity
 * @sample com.zeoharlem.ztourguide.samples.CreateZtourGuideStepConfigExample
 */
data class DisplayProperty(
    val title: String,
    val subTitle: String,
    val offsetPadding: Dp = 0.dp,
    val titleColor: Color = Color.White,
    val subTitleColor: Color = Color.White,
    val displayShape: UsedShape = UsedShape.CIRCLE,
    val arrowDirection: Direction = Direction.BOTTOM,
    val arrowPaddingToTargetItem: Float = 10f,
    val outerSpace: Float = 20f,
    val blurOpacity: Float = 0.8f
) {
    enum class UsedShape {
        RECTANGLE, SQUARE, CIRCLE
    }

    val pointerDisplayTop get() = arrowDirection != Direction.BOTTOM
}

enum class Direction {
    TOP, BOTTOM
}

internal class CustomTriangle(private val arrowDirection: Direction) : Shape {
    override fun createOutline(size: Size, layoutDirection: LayoutDirection, density: Density): Outline {
        val rect = Rect(Offset.Zero, size)
        val trianglePath = if (arrowDirection == Direction.TOP) {
            Path().apply {
                moveTo(rect.topCenter)
                lineTo(rect.bottomRight)
                lineTo(rect.bottomLeft)
                close()
            }
        } else {
            Path().apply {
                moveTo(rect.bottomCenter)
                lineTo(rect.topRight)
                lineTo(rect.topLeft)
                close()
            }

        }
        return Outline.Generic(path = trianglePath)
    }
}

internal fun Path.moveTo(offset: Offset) = moveTo(offset.x, offset.y)

internal fun Path.lineTo(offset: Offset) = lineTo(offset.x, offset.y)