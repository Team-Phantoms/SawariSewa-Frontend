package com.example.sawariapatkalinsewa.api

import com.example.sawariapatkalinsewa.entity.Mechanic
import com.example.sawariapatkalinsewa.entity.client
import com.example.sawariapatkalinsewa.response.GetClientResponse
import com.example.sawariapatkalinsewa.response.LoginResponse
import com.example.sawariapatkalinsewa.response.ViewRequestResponse
import retrofit2.Response
import retrofit2.http.*

interface MechanicAPI {
    @POST("insert/mech")
    suspend fun registerMech(
            @Body mechanic: Mechanic
    ): Response<LoginResponse>

    @FormUrlEncoded
    @POST("mechanic/login")
    suspend fun checkMech(
            @Field("clusername") mechusername : String,
            @Field("clpassword") mechpassword : String
    ):Response<LoginResponse>

    @GET("logout")
    suspend fun logout(
            @Header("Authorization") token: String
    ):Response<LoginResponse>

    @POST("mechanic/webget")
    suspend fun getMech(
        @Header("Authorization") token : String,
    ):Response<GetClientResponse>

    @POST("client/request/get")
    suspend fun getRequest(
            @Header("Authorization") token : String
    ):Response<ViewRequestResponse>
}