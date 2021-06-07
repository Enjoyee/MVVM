package com.glimmer.mvvm.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.glimmer.requestdsl.request.APIDsl

open class RequestViewModel : ViewModel() {
    private val apiException = MutableLiveData<Throwable>()
    private val apiLoading = MutableLiveData<Boolean>()

    /*=======================================================================*/
    private fun <Response> api(apiDSL: APIDsl<Response>.() -> Unit) {
        APIDsl<Response>().apply(apiDSL).launch(viewModelScope)
    }

    /*=======================================================================*/
    protected fun <Response> apiDsl(apiDSL: APIDsl<Response>.() -> Unit) {
        api<Response> {
            onStart {
                apiLoading.value = true
                APIDsl<Response>().apply(apiDSL).onStart?.invoke()
            }

            onRequest {
                APIDsl<Response>().apply(apiDSL).request()
            }

            onResponse {
                APIDsl<Response>().apply(apiDSL).onResponse?.invoke(it)
            }

            onError { error ->
                apiLoading.value = false
                apiException.value = error
                APIDsl<Response>().apply(apiDSL).onError?.invoke(error)
            }

            onFinally {
                apiLoading.value = false
                APIDsl<Response>().apply(apiDSL).onFinally?.invoke()
            }
        }
    }

}

