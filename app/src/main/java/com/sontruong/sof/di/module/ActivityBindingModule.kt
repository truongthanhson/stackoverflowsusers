package com.sontruong.sof.di.module

import com.sontruong.sof.ui.userlist.UserListActivity
import com.sontruong.sof.ui.userlist.UserListModule
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityBindingModule {

    @ContributesAndroidInjector(modules =[UserListModule::class])
    internal abstract fun bindUserListActivity(): UserListActivity
}