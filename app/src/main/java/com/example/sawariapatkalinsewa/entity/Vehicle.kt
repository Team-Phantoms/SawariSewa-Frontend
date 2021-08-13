package com.example.sawariapatkalinsewa.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Vehicle (
        var vechbrand: String? = null,
        var vechmodel: String? = null,
        var vechplatenum: String? = null,
        var clusername: String? = null,
)
{
    @PrimaryKey
    var vid:Int=0
}