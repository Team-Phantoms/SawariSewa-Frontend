package com.example.sawariapatkalinsewa.response


import com.example.sawariapatkalinsewa.entity.workHistory

data class workHistoryResponse (
        val success:Boolean?=null,
        val workd: workHistory?=null,
        val rdata: MutableList<workHistory>,
        val wdata: MutableList<workHistory>
        )