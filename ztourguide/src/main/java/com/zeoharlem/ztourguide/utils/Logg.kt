package com.zeoharlem.ztourguide.utils

import android.util.Log

internal const val appLogTag = "ZtourGuideLib"

internal object Logg {
    fun debug(msg: String?, t: Throwable? = null) {
        Log.d(appLogTag, msg ?: "", t)
    }

    fun info(msg: String?, t: Throwable? = null) {
        Log.i(appLogTag, msg ?: "", t)
    }
}