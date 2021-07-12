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
import java.io.File

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

    @BindingAdapter("leftResIcon")
    @JvmStatic
    fun leftResIcon(view: TextView, res: Int?) {
        res?.let { view.setCompoundDrawablesWithIntrinsicBounds(res, 0, 0, 0) }
    }

    @BindingAdapter("topIcon")
    @JvmStatic
    fun topIcon(view: TextView, drawable: Drawable?) {
        drawable?.let { view.setCompoundDrawablesWithIntrinsicBounds(null, drawable, null, null) }
    }

    @BindingAdapter("topResIcon")
    @JvmStatic
    fun topResIcon(view: TextView, res: Int?) {
        res?.let { view.setCompoundDrawablesWithIntrinsicBounds(0, res, 0, 0) }
    }

    @BindingAdapter("rightIcon")
    @JvmStatic
    fun rightIcon(view: TextView, drawable: Drawable?) {
        drawable?.let { view.setCompoundDrawablesWithIntrinsicBounds(null, null, drawable, null) }
    }

    @BindingAdapter("rightResIcon")
    @JvmStatic
    fun rightResIcon(view: TextView, res: Int?) {
        res?.let { view.setCompoundDrawablesWithIntrinsicBounds(0, 0, res, 0) }
    }

    @BindingAdapter("bottomIcon")
    @JvmStatic
    fun bottomIcon(view: TextView, drawable: Drawable?) {
        drawable?.let { view.setCompoundDrawablesWithIntrinsicBounds(null, null, null, drawable) }
    }

    @BindingAdapter("bottomResIcon")
    @JvmStatic
    fun bottomResIcon(view: TextView, res: Int?) {
        res?.let { view.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, res) }
    }

    @BindingAdapter("height")
    @JvmStatic
    fun height(view: View, size: Int?) {
        size?.let {
            view.layoutParams.height = size
        }
    }

    @BindingAdapter(value = ["netImg", "placeDrawableId"], requireAll = false)
    @JvmStatic
    fun netImg(view: ImageView, url: String?, placeholder: Int?) {
        url?.let {
            view.load(url) {
                placeholder?.let { placeholder(it) }
            }
        }
    }

    @BindingAdapter("localImg")
    @JvmStatic
    fun localImg(view: ImageView, path: String?) {
        path?.let { view.load(File(path)) }
    }

    @BindingAdapter("localImgUri")
    @JvmStatic
    fun localImgUri(view: ImageView, uri: String?) {
        uri?.let { view.load(uri) }
    }

    @BindingAdapter(value = ["netImg", "placeDrawableId"], requireAll = false)
    @JvmStatic
    fun netImg(view: ShapeableImageView, url: String?, placeholder: Int?) {
        view.load(url) {
            placeholder?.let { placeholder(it) }
        }
    }

}