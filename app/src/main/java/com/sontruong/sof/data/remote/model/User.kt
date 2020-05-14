package com.sontruong.sof.data.remote.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class User(
    @SerializedName("account_id")
    val accountId: Long,
    @SerializedName("display_name")
    val displayName: String,
    @SerializedName("profile_image")
    val profileImage: String
) : Parcelable