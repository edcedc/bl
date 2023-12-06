package com.bl.xd.bean
import android.annotation.SuppressLint
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@SuppressLint("ParcelCreator")
@Parcelize
data class UserInfo(
    var id: String="",
    var loginId: String="",
    var password: String="",
    var code: Int,
) : Parcelable
