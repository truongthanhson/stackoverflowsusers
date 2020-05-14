package com.sontruong.sof.ui.userlist

import androidx.lifecycle.ViewModel
import com.sontruong.sof.di.ViewModelKey
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class UserListModule {

    @Binds
    @IntoMap
    @ViewModelKey(UsersViewModel::class)
    abstract fun provideUsersViewModel(usersViewModel: UsersViewModel): ViewModel
}