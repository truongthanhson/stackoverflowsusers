package com.sontruong.sof.domain.usecase

import com.sontruong.sof.data.Listing
import com.sontruong.sof.data.remote.model.User
import com.sontruong.sof.domain.UseCase
import com.sontruong.sof.repositories.UserRepository
import javax.inject.Inject

class GetUsersUseCase @Inject constructor(
    private val userRepository: UserRepository
) : UseCase<GetUsersParams, Listing<User>>() {
    override fun execute(param: GetUsersParams): Listing<User> {
         return userRepository.getUserListing(param.pageSize)
    }

}



data class GetUsersParams(
    val pageSize: Int
)