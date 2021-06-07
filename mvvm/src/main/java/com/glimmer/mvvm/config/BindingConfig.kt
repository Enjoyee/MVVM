package com.glimmer.mvvm.config

import android.util.SparseArray
import androidx.annotation.LayoutRes
import androidx.lifecycle.ViewModel

object BindingConfig {

    fun create(configInfo: (Info.() -> Unit)): Info {
        return Info().also(configInfo)
    }

    class Info {
        internal var layout: Int = 0
        internal var vmVariableId: (() -> Int) = { 0 }
        internal var viewModel: (() -> ViewModel?) = { null }
        internal val bindingParams: SparseArray<Any> = SparseArray()

        fun layoutId(@LayoutRes layoutId: Int) {
            layout = layoutId
        }

        fun viewModel(vmVariableId: Int, viewModel: ViewModel) {
            this.vmVariableId = { vmVariableId }
            this.viewModel = { viewModel }
        }

        infix fun <A, B> A.bind(that: B): Pair<A, B> = Pair(this, that)

        fun params(vararg keyValues: Pair<Int, Any>) {
            keyValues.forEach {
                bindingParams.put(it.first, it.second)
            }
        }


    }

}