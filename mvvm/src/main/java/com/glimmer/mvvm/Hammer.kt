package com.glimmer.mvvm

import android.app.Application
import android.util.Log
import com.glimmer.mvvm.lifecycle.ActivityLifecycle
import com.glimmer.mvvm.lifecycle.ApplicationLifecycle
import com.glimmer.mvvm.provider.ContextProvider
import com.glimmer.requestdsl.request.RequestDSL
import com.glimmer.uutil.KLog
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import timber.log.Timber

object Hammer {
    internal lateinit var mConfig: MVVMConfig

    internal val configInit: Boolean
        get() = ::mConfig.isInitialized

    internal fun init(application: Application) {
        ApplicationLifecycle.register(application)
        application.registerActivityLifecycleCallbacks(ActivityLifecycle)
    }

    fun init(config: (MVVMConfig.() -> Unit)) {
        mConfig = MVVMConfig().also(config)
        // net request
        RequestDSL.init(ContextProvider.mApplication, mConfig.mBaseUrl.invoke()) {
            showApiLog(mConfig.mShowLog)
            okHttp(mConfig.mBuildOkHttp)
            retrofit(mConfig.mBuildRetrofit)
            putHeader(mConfig.mHeader)
        }
        // log
        KLog.loggable(mConfig.mShowLog.invoke()).logTag(mConfig.mLogTag.invoke()).buildLog()
        if (Timber.forest().isEmpty()) {
            if (mConfig.mShowLog.invoke()) {
                Timber.plant(Timber.DebugTree())
            } else {
                Timber.plant(object : Timber.Tree() {
                    override fun log(priority: Int, tag: String?, message: String, t: Throwable?) {
                        if (priority >= Log.ERROR) {
                            Timber.tag(tag).e(t)
                        }
                    }
                })
            }
        }
    }

    class MVVMConfig {
        internal var mShowLog: (() -> Boolean) = { true }
        internal var mShowViewLifecycleLog: (() -> Boolean) = { true }
        internal var mShowApiErrToast: (() -> Boolean) = { true }
        internal var mShowApiLoading: (() -> Boolean) = { true }
        internal var mLogTag: (() -> String) = { "Hammer" }
        internal var mBaseUrl: (() -> String) = { "https://github.com/" }
        internal var mHeader: (() -> (Map<String, String>))? = null
        internal var mBuildOkHttp: ((OkHttpClient.Builder) -> OkHttpClient.Builder)? = null
        internal var mBuildRetrofit: ((Retrofit.Builder) -> Retrofit.Builder)? = null

        fun showLog(showLog: (() -> Boolean)) {
            mShowLog = showLog
        }

        fun showViewLifecycleLog(showViewLifecycleLog: (() -> Boolean)) {
            mShowViewLifecycleLog = showViewLifecycleLog
        }

        fun showApiErrToast(showApiErrToast: (() -> Boolean)) {
            mShowApiErrToast = showApiErrToast
        }

        fun showApiLoading(showApiLoading: (() -> Boolean)) {
            mShowApiLoading = showApiLoading
        }

        fun logTag(logTag: (() -> String)) {
            mLogTag = logTag
        }

        fun baseUrl(baseUrl: (() -> String)) {
            mBaseUrl = baseUrl
        }

        fun putHeaders(header: () -> (Map<String, String>)) {
            mHeader = header
        }

        fun okHttp(builder: ((OkHttpClient.Builder) -> OkHttpClient.Builder)?) {
            mBuildOkHttp = builder
        }

        fun retrofit(builder: ((Retrofit.Builder) -> Retrofit.Builder)?) {
            mBuildRetrofit = builder
        }
    }
}