package com.example.sawariapatkalinsewa.response

import com.example.sawariapatkalinsewa.entity.Vehicle

data class VehicleResponse (
        val success : Boolean? = null,
        val vehicle: Vehicle? =null,
        )