package com.sontruong.sof.di.module
import com.sontruong.sof.data.remote.UserDataSource
import com.sontruong.sof.data.remote.UserDataSourceImpl
import dagger.Binds
import dagger.Module

@Module(includes = [
    NetworkModule::class
])
abstract class DataModule {

    @Binds
    abstract fun bindUserDataSource(source: UserDataSourceImpl): UserDataSource
}