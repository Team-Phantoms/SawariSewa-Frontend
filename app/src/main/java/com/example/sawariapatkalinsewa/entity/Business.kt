package com.example.sawariapatkalinsewa.entity

import android.os.Parcel
import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Business(
    var _id:String?=null,
    var fullname: String? = null,
    var phone: String? = null,
    var gender: String? = null,
    var address: String? = null,
    var lat:String?=null,
    var long:String?=null,
    var mechusername:String?=null
): Parcelable {
    @PrimaryKey(autoGenerate  = true)
    var bId: Int = 0

    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString()
    ) {
        bId = parcel.readInt()
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(_id)
        parcel.writeString(fullname)
        parcel.writeString(phone)
        parcel.writeString(gender)
        parcel.writeString(address)
        parcel.writeString(lat)
        parcel.writeString(long)
        parcel.writeString(mechusername)
        parcel.writeInt(bId)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Business> {
        override fun createFromParcel(parcel: Parcel): Business {
            return Business(parcel)
        }

        override fun newArray(size: Int): Array<Business?> {
            return arrayOfNulls(size)
        }
    }
}

