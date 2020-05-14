package com.sontruong.sof.data.remote.model

import com.google.gson.annotations.SerializedName

data class GetUsersResponse(
    @SerializedName("has_more")
    val hasMore: Boolean,
    @SerializedName("items")
    val items: List<User>,
    @SerializedName("quota_max")
    val quotaMax: Int,
    @SerializedName("quota_remaining")
    val quotaRemaining: Int
)