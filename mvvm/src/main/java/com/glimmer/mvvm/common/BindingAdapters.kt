package com.glimmer.mvvm.common

import android.graphics.drawable.Drawable
import android.graphics.drawable.GradientDrawable
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.ColorInt
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.databinding.BindingAdapter
import coil.load
import com.glimmer.uutil.doWithTry
import com.glimmer.uutil.getColorById
import com.google.android.material.imageview.ShapeableImageView

object BindingAdapters {

    @BindingAdapter("bgColor")
    @JvmStatic
    fun bgColor(view: View, @ColorInt color: Int?) {
        color?.let { view.setBackgroundColor(color) }
    }

    @BindingAdapter("tvColor")
    @JvmStatic
    fun tvColor(view: TextView, @ColorRes color: Int?) {
        doWithTry {
            color?.let { view.setTextColor(view.context.getColorById(color)) }
        }
    }

    @BindingAdapter("bgGradientColors")
    @JvmStatic
    fun gradientColor(view: View, bgColors: IntArray?) {
        if (bgColors == null) return
        doWithTry {
            view.background = GradientDrawable().apply {
                gradientType = GradientDrawable.LINEAR_GRADIENT
                if (bgColors.size == 1) {
                    setColor(view.context.getColorById(bgColors[0]))
                } else {
                    colors = bgColors.let {
                        val colorArr = arrayListOf<Int>()
                        it.forEach { colorId ->
                            colorArr.add(view.context.getColorById(colorId))
                        }
                        colorArr.toIntArray()
                    }
                }
                orientation = GradientDrawable.Orientation.LEFT_RIGHT
            }
        }
    }

    @BindingAdapter("icon")
    @JvmStatic
    fun icon(view: ImageView, @DrawableRes resId: Int?) {
        resId?.let { view.setImageResource(resId) }
    }

    @BindingAdapter("leftIcon")
    @JvmStatic
    fun leftIcon(view: TextView, drawable: Drawable?) {
        drawable?.let { view.setCompoundDrawablesWithIntrinsicBounds(drawable, null, null, null) }
    }

    @BindingAdapter("topIcon")
    @JvmStatic
    fun topIcon(view: TextView, drawable: Drawable?) {
        drawable?.let { view.setCompoundDrawablesWithIntrinsicBounds(null, drawable, null, null) }
    }

    @BindingAdapter("rightIcon")
    @JvmStatic
    fun rightIcon(view: TextView, drawable: Drawable?) {
        drawable?.let { view.setCompoundDrawablesWithIntrinsicBounds(null, null, drawable, null) }
    }

    @BindingAdapter("bottomIcon")
    @JvmStatic
    fun bottomIcon(view: TextView, drawable: Drawable?) {
        drawable?.let { view.setCompoundDrawablesWithIntrinsicBounds(null, null, null, drawable) }
    }

    @BindingAdapter("height")
    @JvmStatic
    fun height(view: View, size: Int?) {
        size?.let {
            view.layoutParams.height = size
        }
    }

    @BindingAdapter("netImg")
    @JvmStatic
    fun netImg(view: ImageView, url: String?) {
        url?.let { view.load(url) }
    }

    @BindingAdapter("netImg")
    @JvmStatic
    fun netImg(view: ShapeableImageView, url: String?) {
        url?.let { view.load(url) }
    }

}