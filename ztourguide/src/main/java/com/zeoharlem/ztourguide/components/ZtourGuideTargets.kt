package com.zeoharlem.ztourguide.components

import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.layout.boundsInWindow
import androidx.compose.ui.layout.onGloballyPositioned

@Composable
fun ZtourGuideWrapper(
    key: String,
    positionMap: MutableMap<String, Rect>,
    content: @Composable () -> Unit
) {
    Box(
        modifier = Modifier.onGloballyPositioned { coords ->
            val rect = coords.boundsInWindow()
            positionMap[key] = Rect(
                rect.left, rect.top, rect.right, rect.bottom
            )
        }
    ) {
        content.invoke()
    }
}