package com.glimmer.mvvm.lifecycle

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.glimmer.mvvm.Hammer
import com.glimmer.uutil.KLog

object FragmentLifecycle : FragmentManager.FragmentLifecycleCallbacks() {

    override fun onFragmentAttached(fm: FragmentManager, f: Fragment, context: Context) {
        if (showViewLifecycleLog()) {
            KLog.v("Fragment界面：%s onAttach", f.javaClass.canonicalName)
        }
    }

    override fun onFragmentCreated(fm: FragmentManager, f: Fragment, savedInstanceState: Bundle?) {
        if (showViewLifecycleLog()) {
            KLog.v("Fragment界面：%s onCreate", f.javaClass.canonicalName)
        }
    }

    override fun onFragmentViewCreated(fm: FragmentManager, f: Fragment, v: View, savedInstanceState: Bundle?) {
        if (showViewLifecycleLog()) {
            KLog.v("Fragment界面：%s onViewCreate", f.javaClass.canonicalName)
        }
    }

    override fun onFragmentActivityCreated(fm: FragmentManager, f: Fragment, savedInstanceState: Bundle?) {
        if (showViewLifecycleLog()) {
            KLog.v("Fragment界面：%s onActivityCreated", f.javaClass.canonicalName)
        }
    }

    override fun onFragmentStarted(fm: FragmentManager, f: Fragment) {
        if (showViewLifecycleLog()) {
            KLog.v("Fragment界面：%s onStart", f.javaClass.canonicalName)
        }
    }

    override fun onFragmentResumed(fm: FragmentManager, f: Fragment) {
        if (showViewLifecycleLog()) {
            KLog.v("Fragment界面：%s onResume", f.javaClass.canonicalName)
        }
    }

    override fun onFragmentPaused(fm: FragmentManager, f: Fragment) {
        if (showViewLifecycleLog()) {
            KLog.v("Fragment界面：%s onPause", f.javaClass.canonicalName)
        }
    }

    override fun onFragmentStopped(fm: FragmentManager, f: Fragment) {
        if (showViewLifecycleLog()) {
            KLog.v("Fragment界面：%s onStop", f.javaClass.canonicalName)
        }
    }

    override fun onFragmentSaveInstanceState(fm: FragmentManager, f: Fragment, outState: Bundle) {
        if (showViewLifecycleLog()) {
            KLog.v("Fragment界面：%s onSaveInstanceState", f.javaClass.canonicalName)
        }
    }

    override fun onFragmentViewDestroyed(fm: FragmentManager, f: Fragment) {
        if (showViewLifecycleLog()) {
            KLog.v("Fragment界面：%s onDestroyView", f.javaClass.canonicalName)
        }
    }

    override fun onFragmentDestroyed(fm: FragmentManager, f: Fragment) {
        if (showViewLifecycleLog()) {
            KLog.v("Fragment界面：%s onDestroy", f.javaClass.canonicalName)
        }
    }

    override fun onFragmentDetached(fm: FragmentManager, f: Fragment) {
        if (showViewLifecycleLog()) {
            KLog.v("Fragment界面：%s onDetach", f.javaClass.canonicalName)
        }
    }

    private fun showViewLifecycleLog(): Boolean {
        return if (Hammer.configInit) Hammer.mConfig.mShowViewLifecycleLog.invoke() else false
    }

}