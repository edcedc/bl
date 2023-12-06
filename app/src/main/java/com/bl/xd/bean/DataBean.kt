package com.bl.xd.bean


import android.annotation.SuppressLint
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@SuppressLint("ParcelCreator")
@Parcelize
data class DataBean(
    var id: String="",
    var LoginID: String="",
    var RoNo: String="",
    var PImgfile: String="",
    var Password: String="",
) : Parcelable