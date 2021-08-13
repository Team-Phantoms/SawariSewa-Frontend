package com.example.sawariapatkalinsewa.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Feedback (
        var clusername: String? = null,
        var clemail: String? = null,
        var mechusername: String? = null,
        var message: String? = null,
        )
{
    @PrimaryKey
   var fid:Int=0
}