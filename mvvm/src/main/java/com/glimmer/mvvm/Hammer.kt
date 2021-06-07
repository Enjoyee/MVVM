package com.glimmer.mvvm

import android.app.Application
import com.glimmer.mvvm.lifecycle.ActivityLifecycle
import com.glimmer.mvvm.lifecycle.ApplicationLifecycle
import com.glimmer.mvvm.provider.ContextProvider
import com.glimmer.requestdsl.request.RequestDSL
import com.glimmer.uutil.KLog
import okhttp3.OkHttpClient
import retrofit2.Retrofit

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
            showLog(mConfig.mShowLog)
            okHttp(mConfig.mBuildOkHttp)
            retrofit(mConfig.mBuildRetrofit)
        }
        // log
        KLog.loggable(mConfig.mShowLog.invoke()).logTag(mConfig.mLogTag.invoke()).buildLog()
    }

    class MVVMConfig {
        internal var mShowLog: (() -> Boolean) = { true }
        internal var mShowViewLifecycleLog: (() -> Boolean) = { true }
        internal var mShowApiErrToast: (() -> Boolean) = { true }
        internal var mShowApiLoading: (() -> Boolean) = { true }
        internal var mLogTag: (() -> String) = { "Hammer" }
        internal var mBaseUrl: (() -> String) = { "https://github.com/" }
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

        fun okHttp(builder: ((OkHttpClient.Builder) -> OkHttpClient.Builder)?) {
            mBuildOkHttp = builder
        }

        fun retrofit(builder: ((Retrofit.Builder) -> Retrofit.Builder)?) {
            mBuildRetrofit = builder
        }
    }
}