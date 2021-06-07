package com.glimmer.mvvm.common

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModel
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit

private var lastTime = 0L

@Suppress("NON_PUBLIC_CALL_FROM_PUBLIC_INLINE")
inline fun throttleLast(block: () -> Unit, duration: Long = 500, unit: TimeUnit = TimeUnit.MILLISECONDS) {
    val thresholdValue = when (unit) {
        TimeUnit.NANOSECONDS -> TimeUnit.NANOSECONDS.toMillis(duration)
        TimeUnit.MICROSECONDS -> TimeUnit.MICROSECONDS.toMillis(duration)
        TimeUnit.MILLISECONDS -> TimeUnit.MILLISECONDS.toMillis(duration)
        TimeUnit.SECONDS -> TimeUnit.SECONDS.toMillis(duration)
        TimeUnit.MINUTES -> TimeUnit.MINUTES.toMillis(duration)
        TimeUnit.HOURS -> TimeUnit.HOURS.toMillis(duration)
        TimeUnit.DAYS -> TimeUnit.DAYS.toMillis(duration)
        else -> 0
    }
    val intervalTime = System.currentTimeMillis() - lastTime
    if (intervalTime >= thresholdValue) {
        lastTime = System.currentTimeMillis()
        block.invoke()
    }
}

/**=========================================================**/
fun LifecycleOwner.ui(block: suspend () -> Unit) {
    lifecycleScope.launch(Dispatchers.Main) {
        block.invoke()
    }
}

fun LifecycleOwner.io(block: suspend () -> Unit) {
    lifecycleScope.launch(Dispatchers.IO) {
        block.invoke()
    }
}

fun LifecycleOwner.launch(block: suspend () -> Unit) {
    lifecycleScope.launch(Dispatchers.Default) {
        block.invoke()
    }
}

/**=========================================================**/
fun ViewModel.ui(block: suspend () -> Unit) {
    viewModelScope.launch(Dispatchers.Main) {
        block.invoke()
    }
}

fun ViewModel.io(block: suspend () -> Unit) {
    viewModelScope.launch(Dispatchers.IO) {
        block.invoke()
    }
}

fun ViewModel.launch(block: suspend () -> Unit) {
    viewModelScope.launch(Dispatchers.Default) {
        block.invoke()
    }
}

/**=========================================================**/
