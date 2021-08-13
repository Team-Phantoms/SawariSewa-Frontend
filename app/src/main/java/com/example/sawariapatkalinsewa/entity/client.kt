package com.example.sawariapatkalinsewa.entity

import android.os.Parcel
import android.os.Parcelable


data class client(
        var _id:String?=null,
        var clfname: String? = null,
        var cllname: String? = null,
        var clemail: String?=null,
        var clusername: String? = null,
        var clpassword: String? = null,
        var photo:String?=null

):Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(_id)
        parcel.writeString(clfname)
        parcel.writeString(cllname)
        parcel.writeString(clemail)
        parcel.writeString(clusername)
        parcel.writeString(clpassword)
        parcel.writeString(photo)

    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<client> {
        override fun createFromParcel(parcel: Parcel): client {
            return client(parcel)
        }

        override fun newArray(size: Int): Array<client?> {
            return arrayOfNulls(size)
        }
    }
}