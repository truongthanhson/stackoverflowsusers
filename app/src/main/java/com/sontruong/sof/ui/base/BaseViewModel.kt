package com.sontruong.sof.ui.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sontruong.sof.ui.Event
import io.reactivex.disposables.CompositeDisposable

open class BaseViewModel : ViewModel() {

    protected val compositeDisposable = CompositeDisposable()

    protected val _showLoading: MutableLiveData<Event<Boolean>> = MutableLiveData()
    val showLoading: LiveData<Event<Boolean>>
        get() = _showLoading
    protected val _showError = MutableLiveData<Event<String>>()
    val showError: LiveData<Event<String>>
        get() = _showError


    protected fun showLoading() {
        _showLoading.postValue(Event(true))
    }

    protected fun hideLoading() {
        _showLoading.postValue(Event(false))
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }
}