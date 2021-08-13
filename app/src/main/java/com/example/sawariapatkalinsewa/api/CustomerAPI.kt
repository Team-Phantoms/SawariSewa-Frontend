package com.example.sawariapatkalinsewa.api

import com.example.sawariapatkalinsewa.entity.client
import com.example.sawariapatkalinsewa.response.GetBusinessResponse
import com.example.sawariapatkalinsewa.response.GetClientResponse
import com.example.sawariapatkalinsewa.response.ImageResponse
import com.example.sawariapatkalinsewa.response.LoginResponse
import okhttp3.MultipartBody
import retrofit2.Response
import retrofit2.http.*

interface CustomerAPI {
    @POST("client/insert")
    suspend fun registerUser(
        @Body client: client
    ):Response<LoginResponse>

    //login user
    @FormUrlEncoded
    @POST("client/login")
    suspend fun checkUser(
        @Field("clusername") clusername : String,
        @Field("clpassword") clpassword : String
    ):Response<LoginResponse>


    @POST("client/get/{clusername}")
    suspend fun getclient(
            @Header("Authorization") token : String,
            @Path("clusername") clusername : String
    ):Response<GetClientResponse>

    @Multipart
    @PUT("client/{clusername}/photo")
    suspend fun uploadImage(
            @Header("Authorization") token: String,
            @Path("clusername") clusername: String,
            @Part file: MultipartBody.Part
    ): Response<ImageResponse>

    @GET("logout")
    suspend fun logout(
            @Header("Authorization") token: String
    ):Response<LoginResponse>


}