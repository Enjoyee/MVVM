package com.glimmer.mvvm.view

interface IStateView {
    fun showLoadingLayout() {}

    fun showEmptyLayout() {}

    fun showErrLayout() {}

    fun showContentLayout() {}
}