package com.zeoharlem.ztourguide

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.setValue
import com.zeoharlem.ztourguide.models.ZTourGuideStep

class ZtourGuidePlayManager(private val listTourGuides: List<ZTourGuideStep>) {
    var currentStepPosition by mutableIntStateOf(0)
        private set

    val currentTourStep get() = listTourGuides.getOrNull(currentStepPosition)

    fun nextStep() {
        currentTourStep?.onNextEvent?.invoke()
        if(currentStepPosition < listTourGuides.size - 1) {
            currentStepPosition++
        }
    }

    fun previousStep() {
        if(currentStepPosition > 0) {
            currentStepPosition--
        }
    }

    fun isFirstStep() = currentStepPosition == 0

    fun isLastStep() = currentStepPosition == listTourGuides.size - 1

    fun isEmpty() = listTourGuides.isEmpty()

    fun isNotEmpty() = listTourGuides.isNotEmpty()

    fun reset() {
        currentStepPosition = 0
    }

    fun getTotalSteps() = listTourGuides.size

    fun getCurrentStep() = currentStepPosition + 1

}