package com.sontruong.sof.data.remote

import com.sontruong.sof.data.remote.model.GetUsersResponse
import io.reactivex.Single
import retrofit2.http.*

interface StackOverflowApiService {

    @GET("users")
    fun getUsers(@Query("page") page: Int
                 , @Query("pagesize") pageSize: Int
                 , @Query("site") site: String = "stackoverflow"): Single<GetUsersResponse>
}
