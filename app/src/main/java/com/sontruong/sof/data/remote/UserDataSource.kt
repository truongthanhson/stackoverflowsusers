package com.sontruong.sof.data.remote

import com.sontruong.sof.data.remote.model.GetUsersResponse
import io.reactivex.Single
import javax.inject.Inject

interface UserDataSource {
    fun getUsers(page: Int, pageSize: Int): Single<GetUsersResponse>
}

class UserDataSourceImpl @Inject constructor(
    private val stackOverflowApiService: StackOverflowApiService
): UserDataSource {
    override fun getUsers(page: Int, pageSize: Int) = stackOverflowApiService.getUsers(page, pageSize)

}