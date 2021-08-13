package com.example.sawariapatkalinsewa.response

import com.example.sawariapatkalinsewa.entity.Request

data class ViewRequestResponse (
    val success:Boolean?=null,
    val requestdata: MutableList<Request>
)