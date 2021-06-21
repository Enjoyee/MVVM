package com.glimmer.mvvm.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.util.forEach
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModelProvider
import com.glimmer.mvvm.view.IMvvmFragment
import com.glimmer.mvvm.viewmodel.BaseVM
import com.glimmer.uutil.toastShort
import kotlin.reflect.KClass

abstract class MVVMFragment<VM : BaseVM, DB : ViewDataBinding> : BaseFragment(), IMvvmFragment {
    /**==========================================================**/
    lateinit var binding: DB
    val vm: VM by lazy { ViewModelProvider(this).get(vMClass().java) }

    /**==========================================================**/
    override fun setFragmentContentView() {
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, bindingConfig.layoutId, container, false)
        binding.lifecycleOwner = this
        // 绑定布局vm
        binding.setVariable(
            bindingConfig.vmVariableId.invoke(),
            bindingConfig.viewModel.invoke()
        )
        // 绑定点击
        binding.setVariable(
            bindingConfig.clickerVariableId.invoke(),
            bindingConfig.clicker.invoke()
        )
        // 绑定xml其他参数
        bindingConfig.bindingParams.forEach { key, value ->
            binding.setVariable(key, value)
        }
        return binding.root
    }

    override fun onInit() {
        super.onInit()
        dataObserver()
    }

    override fun dataObserver() {
        super.dataObserver()
        vm.apiException.observe(this, { err ->
            apiRquestErr(err)
        })
        vm.apiLoading.observe(this, { loading ->
            if (loading) {
                showLoadingDialog()
            } else {
                dismissLoadingDialog()
            }
        })
    }

    open fun apiRquestErr(err: Throwable) {
        bindActivity.toastShort(err.message)
    }

    /**==========================================================**/
    abstract fun vMClass(): KClass<VM>
}