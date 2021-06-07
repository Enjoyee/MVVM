package com.glimmer.mvvm.bean

import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import com.blankj.utilcode.util.BarUtils

open class BeanToolBar(
    open var centerTitle: String = "",
    @ColorRes open var centerColor: Int = android.R.color.white,
    @DrawableRes open var leftIcon: Int? = null,
    open var leftText: String = "",
    @ColorRes open var leftColor: Int = android.R.color.white,
    @DrawableRes open var rightIcon: Int? = null,
    open var rightText: String = "",
    @ColorRes open var rightColor: Int = android.R.color.white,
    @ColorRes open var bgColor: Int = android.R.color.black,
    @ColorRes open var bgGradientColors: IntArray? = null,
    open var marginStatusBarHeight: Int = BarUtils.getStatusBarHeight()
) {
    fun bgGradientColor() = if (bgGradientColors == null) intArrayOf(bgColor) else bgGradientColors
}