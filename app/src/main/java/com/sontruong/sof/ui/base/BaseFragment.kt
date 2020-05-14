package com.sontruong.sof.ui.base

import android.content.Context
import androidx.lifecycle.ViewModelProvider
import dagger.android.support.DaggerFragment
import javax.inject.Inject

abstract class BaseFragment : DaggerFragment() {
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    var activity: BaseActivity? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        try {
            activity = context as BaseActivity
        } catch (ex: RuntimeException) {
            throw RuntimeException(context.toString() + "Activity holder must extend BaseActivity")
        }
    }


    protected fun showLoading() {
        activity?.showLoading()
    }

    protected fun hideLoading() {
        activity?.hideLoading()
    }

    protected fun showError(error: String) {
        activity?.showError(error)
    }

    override fun onDetach() {
        super.onDetach()
        activity = null
    }
}