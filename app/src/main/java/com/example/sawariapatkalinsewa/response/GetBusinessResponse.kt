package com.example.sawariapatkalinsewa.response

import com.example.sawariapatkalinsewa.entity.Business

data class GetBusinessResponse (
        val success:Boolean?=null,
        val bdata:ArrayList<Business>?=null

        )
