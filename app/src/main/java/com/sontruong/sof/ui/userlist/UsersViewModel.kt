package com.sontruong.sof.ui.userlist

import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.sontruong.sof.data.Listing
import com.sontruong.sof.data.remote.model.User
import com.sontruong.sof.domain.Result
import com.sontruong.sof.domain.Result.Success
import com.sontruong.sof.domain.usecase.GetUsersParams
import com.sontruong.sof.domain.usecase.GetUsersUseCase
import com.sontruong.sof.ui.base.BaseViewModel
import javax.inject.Inject

class UsersViewModel @Inject constructor(
    private val getUsersUseCase: GetUsersUseCase
) : BaseViewModel() {

    private val _users = MediatorLiveData<Listing<User>>()
    val users = Transformations.switchMap(_users) { it.pagedList }
    val networkState = Transformations.switchMap(_users) { it.networkState }
    val refreshState = Transformations.switchMap(_users) { it.refreshState }
    private val getUsersUseCaseResult = MutableLiveData<Result<Listing<User>>>()

    init {
        _users.addSource(getUsersUseCaseResult) {
            (getUsersUseCaseResult.value as? Success)?.data?.let {
                _users.value = it
            }
        }
        getUsersUseCase(GetUsersParams( PAGE_SIZE), getUsersUseCaseResult)
    }

    fun refresh() {
        _users.value?.refresh?.invoke()
    }

    fun retry() {
        val listing = _users.value
        listing?.retry?.invoke()
    }

    companion object {
        const val PAGE_SIZE = 20
    }
}

