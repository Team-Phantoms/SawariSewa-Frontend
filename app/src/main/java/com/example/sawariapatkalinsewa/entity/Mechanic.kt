package com.example.sawariapatkalinsewa.entity

import android.os.Parcel
import android.os.Parcelable
import androidx.room.PrimaryKey

data class Mechanic  (
        var _id:String?=null,
        var mechfname:String?=null,
        var mechlname:String?=null,
        var mechemail:String?=null,
        var mechusername:String?=null,
        var mechvechtype:String?=null,
        var mechaddress:String?=null,
        var mechPhone:String?=null,
        var mechcitizenship:String?=null,
        var mechworkplace:String?=null,
        var mechPANnum:String?=null,
        var mechpassword:String?=null
        )
