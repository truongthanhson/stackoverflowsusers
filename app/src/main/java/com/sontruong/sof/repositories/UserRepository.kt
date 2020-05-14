package com.sontruong.sof.repositories

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.paging.DataSource
import androidx.paging.LivePagedListBuilder
import androidx.paging.PageKeyedDataSource
import androidx.paging.PagedList
import com.sontruong.sof.data.Listing
import com.sontruong.sof.data.NetworkState
import com.sontruong.sof.data.remote.StackOverflowApiService
import com.sontruong.sof.data.remote.model.User
import io.reactivex.Single
import java.util.concurrent.Executor
import javax.inject.Inject
import javax.inject.Singleton

interface UserRepository {
    fun getUserListing(pageSize: Int): Listing<User>
}

@Singleton
class DefaultUserRepository @Inject constructor(
    private val stackOverflowApiService: StackOverflowApiService,
    private val networkExecutor: Executor
): UserRepository {
    override fun getUserListing(pageSize: Int): Listing<User> {
        val sourceFactory =
            UserDataSourceFactory(stackOverflowApiService, networkExecutor)
        val config = PagedList.Config.Builder()
            .setPageSize(pageSize)
            .setInitialLoadSizeHint(pageSize)
            .setEnablePlaceholders(false)
            .build()
        val livePagedList = LivePagedListBuilder(sourceFactory, config).build()

        val refreshState = Transformations.switchMap(sourceFactory.sourceLiveData) {
            it.initialLoad
        }
        return Listing(
            pagedList = livePagedList,
            networkState = Transformations.switchMap(sourceFactory.sourceLiveData) {
                it.listingState
            },
            retry = {
                sourceFactory.sourceLiveData.value?.retryAllFailed()
            },
            refresh = {
                sourceFactory.sourceLiveData.value?.invalidate()
            },
            refreshState = refreshState
        )
    }

}

class UserDataSourceFactory(
    private val stackOverflowApiService: StackOverflowApiService,
    private val retryExecutor: Executor
) : DataSource.Factory<Int, User>() {
    val sourceLiveData = MutableLiveData<UserssDataSource>()
    override fun create(): DataSource<Int, User> {
        val source =
            UserssDataSource(stackOverflowApiService, retryExecutor)
        sourceLiveData.postValue(source)
        return source
    }
}

class UserssDataSource(
    private val stackOverflowApiService: StackOverflowApiService,
    private val retryExecutor: Executor
) : PageKeyedDataSource<Int, User>() {

    private var retry: (() -> Any)? = null
    val listingState = MutableLiveData<NetworkState>()
    val initialLoad = MutableLiveData<NetworkState>()

    fun retryAllFailed() {
        val prevRetry = retry
        retry = null
        prevRetry?.let {
            retryExecutor.execute {
                it.invoke()
            }
        }
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, User>) {
        // ignored, since we only ever append to our initial load
    }

    override fun loadInitial(
        params: LoadInitialParams<Int>,
        callback: LoadInitialCallback<Int, User>
    ) {
        listingState.postValue(NetworkState.LOADING)
        initialLoad.postValue(NetworkState.LOADING)
        try {
            val all = mutableListOf<User>()
            val users = getUsers(1, params.requestedLoadSize).blockingGet()
            all.addAll(users)
            retry = null
            listingState.postValue(NetworkState.LOADED)
            initialLoad.postValue(NetworkState.LOADED)
            callback.onResult(all, null, 2)
        } catch (ex: Exception) {
            retry = {
                loadInitial(params, callback)
            }
            val error = NetworkState.error(ex.message ?: "unknown error")
            listingState.postValue(error)
            initialLoad.postValue(error)
        }
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, User>) {
        listingState.postValue(NetworkState.LOADING)
        try {
            val users = getUsers(params.key, params.requestedLoadSize).blockingGet()
            retry = null
            callback.onResult(users, params.key + 1)
            listingState.postValue(NetworkState.LOADED)
        } catch (ex: Exception) {
            retry = {
                loadAfter(params, callback)
            }
            listingState.postValue(
                NetworkState.error(ex.message ?: "unknown error")
            )
        }
    }

    private fun getUsers(offset: Int, limit: Int): Single<List<User>> {
        return stackOverflowApiService.getUsers(
            offset,
            limit
        )
            .map {
                val data = it.items ?: throw RuntimeException("Get devices data is null pointer!!!")
                data
            }
    }
}