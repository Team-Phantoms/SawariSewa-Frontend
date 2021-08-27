package com.example.sawariapatkalinsewa.response


import com.example.sawariapatkalinsewa.entity.clientWorkHistory
import com.example.sawariapatkalinsewa.entity.workHistory

data class workHistoryResponse (
        val success:Boolean?=null,
        val workd: workHistory?=null,
        val rdata: MutableList<clientWorkHistory>,
        val mwdata: MutableList<clientWorkHistory>,
        val wdata: MutableList<workHistory>
        )