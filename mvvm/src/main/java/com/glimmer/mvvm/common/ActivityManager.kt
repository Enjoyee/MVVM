package com.glimmer.mvvm.common

import android.app.Activity

object ActivityManager {
    private val activityList by lazy { arrayListOf<Activity>() }

    fun add(activity: Activity) {
        synchronized(ActivityManager::class.java) {
            if (!activityList.contains(activity)) {
                activityList.add(activity)
            }
        }
    }

    fun remove(activity: Activity) {
        synchronized(ActivityManager::class.java) {
            if (activityList.contains(activity)) {
                activityList.remove(activity)
            }
        }
    }

    fun getTop(): Activity? {
        return getActivity(activityList.size - 1)
    }

    fun getBottom(): Activity? {
        return getActivity(0)
    }

    fun getActivity(position: Int): Activity? {
        return if (position < activityList.size) {
            activityList[position]
        } else {
            null
        }
    }

    fun finishAll() {
        synchronized(ActivityManager::class.java) {
            activityList.forEach { activity -> activity.finish() }
        }
    }
}