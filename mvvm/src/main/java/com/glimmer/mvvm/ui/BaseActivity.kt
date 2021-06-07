package com.glimmer.mvvm.ui

import android.content.Context
import android.os.Bundle
import android.util.SparseArray
import android.view.MotionEvent
import android.view.View
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.core.util.containsValue
import com.blankj.utilcode.util.BarUtils
import com.glimmer.mvvm.common.throttleLast
import com.glimmer.mvvm.config.BindingConfig
import com.glimmer.mvvm.view.IActivity
import com.glimmer.uutil.Clicker
import com.glimmer.uutil.KHandler
import com.glimmer.uutil.closeInputKeyboard
import java.util.concurrent.TimeUnit

abstract class BaseActivity : AppCompatActivity(), IActivity, Clicker {
    companion object {
        private const val DURATION_CLICK = 500L
    }

    /**==========================================================**/
    /**
     * 页面参数
     */
    internal val bindingConfig: BindingConfig.Info by lazy { createBindingInfo() }

    /**==========================================================**/
    /**
     * 防止内存泄漏Handler
     */
    val handler by lazy { KHandler(this) }
    val activity: AppCompatActivity by lazy { this }
    var context: Context? = null

    /**
     * 忽略不需要过滤快速点击的view
     */
    private val ignoreMultiClickViewArr = SparseArray<View>()

    /**==========================================================**/
    override fun attachBaseContext(newBase: Context?) {
        super.attachBaseContext(newBase)
        context = newBase
    }

    /**==========================================================**/
    override fun onCreate(savedInstanceState: Bundle?) {
        initWidows()
        super.onCreate(savedInstanceState)
        setActivityContentView()
        setStatusBar()
        onInit()
    }

    internal open fun setActivityContentView() {
        setContentView(bindingConfig.layout)
    }

    /**==========================================================**/
    private fun setStatusBar() {
        BarUtils.transparentStatusBar(this)
        statusBarMode()
    }

    override fun onInit() {
        initData()
        initView()
    }

    override fun initData() {
    }

    override fun initView() {
    }

    override fun onClick(v: View?) {
        v?.let {
            val contain = ignoreMultiClickViewArr.containsValue(it)
            if (contain) {
                viewClick(it)
            } else {
                throttleLast({
                    viewClick(it)
                }, clickDuration(), clickDurationTimeUnit())
            }
        }
    }

    /**==========================================================**/
    fun addViewsClick(viewArr: (() -> List<View>)) {
        viewArr.invoke().forEach {
            it.setOnClickListener(this)
        }
    }

    /**
     * 忽略过滤多次点击的view
     */
    fun filterMultiClickStrategy(viewArr: (() -> List<View>)) {
        viewArr.invoke().forEach {
            val contain = ignoreMultiClickViewArr.containsValue(it)
            if (!contain) {
                ignoreMultiClickViewArr.put(ignoreMultiClickViewArr.size(), it)
            }
        }
    }

    /**
     * 过滤点击的时长
     */
    open fun clickDuration() = DURATION_CLICK

    /**
     * 过滤点击的时长单位
     */
    open fun clickDurationTimeUnit() = TimeUnit.MILLISECONDS

    /**
     * 状态栏字体是否黑色模式
     */
    open fun statusBarMode(blackFont: Boolean = false) {
        BarUtils.setStatusBarLightMode(this, blackFont)
    }

    /**
     * 是否设置view距离状态栏高度
     */
    fun addViewMarginStatusBar(view: View) {
        BarUtils.addMarginTopEqualStatusBarHeight(view)
    }

    /**==========================================================**/
    override fun dispatchTouchEvent(ev: MotionEvent): Boolean {
        if (ev.action == MotionEvent.ACTION_DOWN) {
            if (isShouldHideKeyboard(currentFocus, ev)) {
                closeInputKeyboard()
            }
        }
        return super.dispatchTouchEvent(ev)
    }

    open fun isShouldHideKeyboard(v: View?, event: MotionEvent): Boolean {
        if (v is EditText) {
            var result = true
            filterHideKeyboardView().forEach {
                result = result && isEventInViewBound(it, event)
            }
            return result && isEventInViewBound(v, event)
        }
        // 如果焦点不是EditText则忽略，这个发生在视图刚绘制完，第一个焦点不在EditText上，和用户用轨迹球选择其他的焦点
        return false
    }

    private fun isEventInViewBound(v: View, event: MotionEvent): Boolean {
        val l = intArrayOf(0, 0)
        v.getLocationInWindow(l)
        val left = l[0]
        val top = l[1]
        val bottom = top + v.height
        val right = left + v.width
        return event.x <= left || event.x >= right || event.y <= top || event.y >= bottom
    }

    open fun filterHideKeyboardView(): List<View> = emptyList()

    /**==========================================================**/
    abstract fun createBindingInfo(): BindingConfig.Info

    open fun viewClick(v: View) {}

}