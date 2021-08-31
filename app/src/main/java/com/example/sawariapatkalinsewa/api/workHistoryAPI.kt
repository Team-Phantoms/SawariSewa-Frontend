package com.example.sawariapatkalinsewa.api


import com.example.sawariapatkalinsewa.entity.workHistory
import com.example.sawariapatkalinsewa.response.DeleteBusinessResponse
import com.example.sawariapatkalinsewa.response.ViewRequestResponse
import com.example.sawariapatkalinsewa.response.workHistoryResponse
import retrofit2.Response
import retrofit2.http.*

interface workHistoryAPI {
    @POST("insert/work")
    suspend fun insertWork(
            @Header("Authorization") token : String,
            @Body workHistory: workHistory
    ): Response<workHistoryResponse>

//for customer to view driver
    @POST("get/driver")
    suspend fun getDriver(
            @Header("Authorization") token : String
    ):Response<workHistoryResponse>

//for mechanic to view history
    @POST("get/work")
    suspend fun getWork(
            @Header("Authorization") token : String
    ):Response<workHistoryResponse>

    //delete on reject click api
    @DELETE("work/delete/{id}")
    suspend fun deleteReq(
        @Path("id") id : String
    ):Response<DeleteBusinessResponse>

}