package com.sontruong.sof.di.module

import com.sontruong.sof.data.remote.StackOverflowApiService
import com.sontruong.sof.repositories.DefaultUserRepository
import com.sontruong.sof.repositories.UserRepository
import dagger.Module
import dagger.Provides
import java.util.concurrent.Executor

@Module(includes = [
    DataModule::class
])
class RepositoryModule {

    @Provides()
    fun provideUserRepository(
        stackOverflowApiService: StackOverflowApiService,
        networkExecutor: Executor
    ): UserRepository = DefaultUserRepository(stackOverflowApiService, networkExecutor)
}