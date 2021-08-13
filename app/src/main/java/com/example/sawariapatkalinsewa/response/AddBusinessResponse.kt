package com.example.sawariapatkalinsewa.response

import com.example.sawariapatkalinsewa.entity.Business

data class AddBusinessResponse (
    val success : Boolean? = null,
    val business:Business? =null,
)