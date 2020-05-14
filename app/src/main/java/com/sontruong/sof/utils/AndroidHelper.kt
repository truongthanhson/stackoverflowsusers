package com.sontruong.sof.utils

import android.content.Context
import android.content.res.Configuration
import android.content.res.Resources
import android.graphics.Point
import android.os.Handler
import android.util.DisplayMetrics
import android.view.WindowManager
import kotlin.math.abs
import kotlin.math.ceil

object AndroidHelper {
    private var density = Resources.getSystem().displayMetrics.density
    private var applicationHandler: Handler? = null

    var displaySize = Point()
    var displayMetrics = DisplayMetrics()

    fun init(applicationContext: Context) {
        applicationHandler = Handler(applicationContext.mainLooper)
        density = applicationContext.resources.displayMetrics.density
        updateScreenSize(applicationContext)
    }

    private fun updateScreenSize(context: Context) {
        val configuration = context.resources.configuration
        val manager = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        val display = manager.defaultDisplay
        if (display != null) {
            display.getMetrics(displayMetrics)
            display.getSize(displaySize)
        }
        if (configuration.screenWidthDp != Configuration.SCREEN_WIDTH_DP_UNDEFINED) {
            val newSize = ceil((configuration.screenWidthDp * density).toDouble()).toInt()
            if (abs(displaySize.x - newSize) > 3) {
                displaySize.x = newSize
            }
        }
        if (configuration.screenHeightDp != Configuration.SCREEN_HEIGHT_DP_UNDEFINED) {
            val newSize = ceil((configuration.screenHeightDp * density).toDouble()).toInt()
            if (abs(displaySize.y - newSize) > 3) {
                displaySize.y = newSize
            }
        }
    }

    fun dpToPx(dp: Int): Int {
        return (dp * density).toInt()
    }

    fun dpToPx(dp: Float): Float {
        return dp * density
    }

    fun runOnUIThread(runnable: Runnable) {
        runOnUIThread(runnable, 0)
    }

    fun runOnUIThread(runnable: Runnable, delay: Long) {
        if (delay == 0L) {
            applicationHandler!!.post(runnable)
        } else {
            applicationHandler!!.postDelayed(runnable, delay)
        }
    }

}
