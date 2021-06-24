package com.glimmer.mvvm.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.glimmer.mvvm.bean.BeanErr
import com.glimmer.mvvm.bean.IResponse
import com.glimmer.requestdsl.request.APIDsl
import com.glimmer.requestdsl.request.RequestDSL
import kotlin.reflect.KClass

open class RequestViewModel : ViewModel() {
    internal val apiException = MutableLiveData<BeanErr>()
    internal val apiLoading = MutableLiveData<Boolean>()

    /*=======================================================================*/
    fun <IApiService : Any> createApiService(iApiService: KClass<IApiService>): IApiService {
        return RequestDSL.createApiService(iApiService.java)
    }

    /*=======================================================================*/
    private fun <Response : IResponse> api(apiDSL: APIDsl<Response>.() -> Unit) {
        APIDsl<Response>().apply(apiDSL).launch(viewModelScope)
    }

    /*=======================================================================*/
    protected fun <Response : IResponse> apiRequest(apiDSL: APIDsl<Response>.() -> Unit) {
        api<Response> {
            onStart {
                apiLoading.value = true
                APIDsl<Response>().apply(apiDSL).onStart?.invoke()
            }

            onRequest {
                APIDsl<Response>().apply(apiDSL).request()
            }

            onResponse {
                if (it.success()) {
                    APIDsl<Response>().apply(apiDSL).onResponse?.invoke(it)
                } else {
                    apiException.value = BeanErr(it.reCode(), err = it.errMsg())
                    APIDsl<Response>().apply(apiDSL).onError?.invoke(Exception(it.errMsg()))
                }
            }

            onError { error ->
                apiException.value = BeanErr(null, err = error.localizedMessage)
                APIDsl<Response>().apply(apiDSL).onError?.invoke(error)
            }

            onFinally {
                apiLoading.value = false
                APIDsl<Response>().apply(apiDSL).onFinally?.invoke()
            }
        }
    }

}

