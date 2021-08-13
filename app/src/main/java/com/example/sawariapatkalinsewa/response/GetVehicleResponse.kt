package com.example.sawariapatkalinsewa.response

import com.example.sawariapatkalinsewa.entity.Feedback
import com.example.sawariapatkalinsewa.entity.Vehicle

data class GetVehicleResponse (
        val success:Boolean?=null,
        val vdata:ArrayList<Vehicle>?=null
        )