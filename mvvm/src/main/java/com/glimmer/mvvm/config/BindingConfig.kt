package com.glimmer.mvvm.config

import android.util.SparseArray
import androidx.annotation.LayoutRes
import androidx.lifecycle.ViewModel
import com.glimmer.uutil.Clicker

object BindingConfig {

    fun create(configInfo: (Info.() -> Unit)): Info {
        return Info().also(configInfo)
    }

    class Info {
        internal var layoutId: Int = 0
        internal var vmVariableId: (() -> Int) = { 0 }
        internal var viewModel: (() -> ViewModel?) = { null }
        internal var clickerVariableId: (() -> Int) = { 0 }
        internal var clicker: (() -> Clicker?) = { null }
        internal val bindingParams: SparseArray<Any> = SparseArray()

        fun layoutId(@LayoutRes layoutId: Int) {
            this.layoutId = layoutId
        }

        fun viewModel(vmVariableId: Int, viewModel: ViewModel) {
            this.vmVariableId = { vmVariableId }
            this.viewModel = { viewModel }
        }

        fun clicker(clickerVariableId: Int, clicker: Clicker) {
            this.clickerVariableId = { clickerVariableId }
            this.clicker = { clicker }
        }

        infix fun <A, B> A.bind(that: B): Pair<A, B> = Pair(this, that)

        fun params(vararg keyValues: Pair<Int, Any>) {
            keyValues.forEach {
                bindingParams.put(it.first, it.second)
            }
        }

    }

}