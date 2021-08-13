package com.example.sawariapatkalinsewa.api

import com.example.sawariapatkalinsewa.entity.Business
import com.example.sawariapatkalinsewa.entity.client
import com.example.sawariapatkalinsewa.response.AddBusinessResponse
import com.example.sawariapatkalinsewa.response.DeleteBusinessResponse
import com.example.sawariapatkalinsewa.response.GetBusinessResponse
import com.example.sawariapatkalinsewa.response.LoginResponse
import retrofit2.Response
import retrofit2.http.*

interface BusinessAPI {
    @POST("business/insert")
    suspend fun registerBusiness(
        @Header("Authorization") token : String,
        @Body business: Business
    ): Response<AddBusinessResponse>

    @POST("business/all")
    suspend fun getBusiness(
            @Header("Authorization") token : String
    ):Response<GetBusinessResponse>

    @POST("business/mech/{mechusername}")
    suspend fun getmechBusiness(
        @Header("Authorization") token : String,
        @Path("mechusername") mechusername : String
    ):Response<GetBusinessResponse>

    @DELETE("business/delete/{id}")
    suspend fun deletebusiness(
        @Header("Authorization") token : String,
        @Path("id") id : String
    ):Response<DeleteBusinessResponse>

    @PUT("business/update/{id}")
    suspend fun updateBusiness(
        @Header("Authorization") token : String,
        @Path("id") id : String,
        @Body business: Business
    ):Response<DeleteBusinessResponse>


}