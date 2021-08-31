package com.example.sawariapatkalinsewa.api

import com.example.sawariapatkalinsewa.entity.clientWorkHistory
import com.example.sawariapatkalinsewa.entity.workHistory
import com.example.sawariapatkalinsewa.response.DeleteBusinessResponse
import com.example.sawariapatkalinsewa.response.workHistoryResponse
import retrofit2.Response
import retrofit2.http.*

interface clientworkhistoryAPI {
    @POST("insert/client_work")
    suspend fun insertclientWork(
        @Header("Authorization") token : String,
        @Body clientWorkHistory: clientWorkHistory
    ): Response<workHistoryResponse>

    //for customer to view driver
    @POST("get/clientdriver")
    suspend fun getClientDriver(
        @Header("Authorization") token : String
    ): Response<workHistoryResponse>

    @POST("get/mwork")
    suspend fun getClient(
        @Header("Authorization") token : String
    ): Response<workHistoryResponse>

    @DELETE("cwork/delete/{id}")
    suspend fun deleteReq(
        @Path("id") id : String
    ):Response<DeleteBusinessResponse>

}