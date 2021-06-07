package com.glimmer.mvvm.viewmodel

import androidx.lifecycle.MutableLiveData
import com.glimmer.mvvm.bean.BeanToolBar

open class BaseVM : RequestViewModel() {
    val beanToolBar by lazy { MutableLiveData<BeanToolBar>() }
}