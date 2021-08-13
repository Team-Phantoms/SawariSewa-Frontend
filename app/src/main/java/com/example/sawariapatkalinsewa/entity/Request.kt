package com.example.sawariapatkalinsewa.entity

import android.os.Parcel
import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey


data class Request (
    var problemtype:String?=null,
    var vechbrand: String? = null,
    var vechmodel: String? = null,
    var vechplatenum: String? = null,
    var address: String? = null,
    var lat:String?=null,
    var long:String?=null,
    var clusername: String? = null)
    :Parcelable {
    constructor(parcel: Parcel) : this(
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString()) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(problemtype)
        parcel.writeString(vechbrand)
        parcel.writeString(vechmodel)
        parcel.writeString(vechplatenum)
        parcel.writeString(address)
        parcel.writeString(lat)
        parcel.writeString(long)
        parcel.writeString(clusername)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Request> {
        override fun createFromParcel(parcel: Parcel): Request {
            return Request(parcel)
        }

        override fun newArray(size: Int): Array<Request?> {
            return arrayOfNulls(size)
        }
    }
}
