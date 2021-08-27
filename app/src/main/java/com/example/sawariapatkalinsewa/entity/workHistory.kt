package com.example.sawariapatkalinsewa.entity

import android.os.Parcel
import android.os.Parcelable

data class workHistory  (
        var problemtype:String?=null,
        var address: String? = null,
        var lat:String?=null,
        var long:String?=null,
        var token:String?=null,
        var clusername: String? = null,
        var mechusername:String?=null,
        var mechphone:String?=null,
        var accepted:String?=null,
        var rejected:String?=null,
        var contact:String?=null,
):Parcelable {
    constructor(parcel: Parcel) : this(
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
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
        parcel.writeString(problemtype)
        parcel.writeString(address)
        parcel.writeString(lat)
        parcel.writeString(long)
        parcel.writeString(token)
        parcel.writeString(clusername)
        parcel.writeString(mechusername)
        parcel.writeString(mechphone)
        parcel.writeString(accepted)
        parcel.writeString(rejected)
        parcel.writeString(contact)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<workHistory> {
        override fun createFromParcel(parcel: Parcel): workHistory {
            return workHistory(parcel)
        }

        override fun newArray(size: Int): Array<workHistory?> {
            return arrayOfNulls(size)
        }
    }
}