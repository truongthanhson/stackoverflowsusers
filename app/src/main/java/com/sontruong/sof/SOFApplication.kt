package com.sontruong.sof

import com.sontruong.sof.utils.AndroidHelper
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication
import timber.log.Timber

class SOFApplication : DaggerApplication() {

    override fun onCreate() {
        super.onCreate()
        AndroidHelper.init(this)
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }


    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        return DaggerAppComponent
            .factory()
            .create(this)
    }


}
