package com.glimmer.mvvm.view

interface IActivity : IView, IStateView, ILoadingDialog {
    fun initWidows() {}
}