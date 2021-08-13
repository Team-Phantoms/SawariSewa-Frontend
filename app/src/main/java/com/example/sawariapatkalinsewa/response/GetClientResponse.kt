package com.example.sawariapatkalinsewa.response


import com.example.sawariapatkalinsewa.entity.Mechanic
import com.example.sawariapatkalinsewa.entity.client

data class GetClientResponse (
        val success:Boolean?=null,
        val cprofile:ArrayList<client>?=null,
        val profile:ArrayList<Mechanic>?=null
        )