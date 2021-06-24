package com.glimmer.mvvm.bean

open interface IResponse {
    fun success() = true

    fun reCode() = 0

    fun errMsg() = "请求出错~"
}