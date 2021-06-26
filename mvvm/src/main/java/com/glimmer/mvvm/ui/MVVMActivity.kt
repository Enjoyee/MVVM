package com.glimmer.mvvm.ui

import android.view.View
import androidx.core.util.forEach
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModelProvider
import com.blankj.utilcode.util.ToastUtils
import com.glimmer.mvvm.R
import com.glimmer.mvvm.view.IMvvmActivity
import com.glimmer.mvvm.viewmodel.BaseVM
import com.glimmer.uutil.toastShort
import kotlin.reflect.KClass

abstract class MVVMActivity<VM : BaseVM, DB : ViewDataBinding> : BaseActivity(), IMvvmActivity {
    /**==========================================================**/
    val binding: DB by lazy { DataBindingUtil.setContentView(this, bindingConfig.layoutId) }
    val vm: VM by lazy { ViewModelProvider(this).get(vMClass().java) }

    /**==========================================================**/
    override fun setActivityContentView() {
    }

    override fun onInit() {
        // 绑定布局vm
        bindingConfig.viewModel.invoke()?.let { vm ->
            binding.setVariable(bindingConfig.vmVariableId.invoke(), vm)
        }
        // 绑定点击
        bindingConfig.clicker.invoke()?.let { clicker ->
            binding.setVariable(bindingConfig.clickerVariableId.invoke(), clicker)
        }
        // 绑定xml其他参数
        bindingConfig.bindingParams.forEach { key, value ->
            binding.setVariable(key, value)
        }
        binding.lifecycleOwner = this
        super.onInit()
        dataObserver()
    }

    override fun viewClick(v: View) {
        when (v.id) {
            R.id.tvLeft -> toolBarLeftClick(v)
            R.id.tvRight -> toolBarRightClick(v)
        }
    }

    /**==========================================================**/
    abstract fun vMClass(): KClass<VM>

    /**==========================================================**/
    open fun toolBarLeftClick(v: View) {
        finish()
    }

    open fun toolBarRightClick(v: View) {}

    override fun dataObserver() {
        super.dataObserver()
        vm.apiException.observe(this, { err ->
            apiRquestErr(err.code, err.err)
        })
        vm.apiLoading.observe(this, { loading ->
            if (loading) {
                apiLoading()
            } else {
                apiFinish()
            }
        })
    }

    open fun apiLoading() {
        showLoadingDialog()
    }

    open fun apiFinish() {
        dismissLoadingDialog()
    }

    open fun apiRquestErr(code: Int?, err: String?) {
        ToastUtils.showLong(err)
    }

}