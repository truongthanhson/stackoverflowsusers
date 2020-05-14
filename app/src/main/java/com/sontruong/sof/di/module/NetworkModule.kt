package com.sontruong.sof.di.module

import android.content.Context
import com.sontruong.sof.data.remote.StackOverflowApiService
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.sontruong.sof.data.remote.URL_SERVER
import dagger.Module
import dagger.Provides
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.Executor
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors
import javax.inject.Singleton

@Module
class NetworkModule {

    @Singleton
    @Provides
    fun provideOkHttpCache(context: Context) = Cache(context.cacheDir, 10 * 1024 * 1024) //chache size = 10MB

    @Singleton
    @Provides
    fun provideGson() = GsonBuilder().create()

    @Singleton
    @Provides
    fun provideLoggingInterceptor(): HttpLoggingInterceptor {
        val logging = HttpLoggingInterceptor()
        logging.level = HttpLoggingInterceptor.Level.BODY
        return logging
    }

    @Singleton
    @Provides
    fun provideOkHttpClient(cache: Cache, logging: HttpLoggingInterceptor) =
            OkHttpClient.Builder().cache(cache).addInterceptor(logging).build()


    @Singleton
    @Provides
    fun provideSOFApiService(gson: Gson, okHttpClient: OkHttpClient): StackOverflowApiService =
        Retrofit.Builder()
            .baseUrl(URL_SERVER)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .client(okHttpClient)
            .build()
            .create(StackOverflowApiService::class.java)

    @Singleton
    @Provides
    fun provideNetworkExecutors(): Executor = Executors.newFixedThreadPool(5)
}