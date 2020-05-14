package com.sontruong.sof.di.module

import androidx.lifecycle.ViewModelProvider
import com.sontruong.sof.di.SOFViewModelFactory
import dagger.Binds
import dagger.Module

@Module
internal abstract class ViewModelModule {

    @Binds
    internal abstract fun provideViewModelFactory(factory: SOFViewModelFactory): ViewModelProvider.Factory
}