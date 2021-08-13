package com.example.sawariapatkalinsewa.api

import com.example.sawariapatkalinsewa.entity.Request
import com.example.sawariapatkalinsewa.entity.Vehicle
import com.example.sawariapatkalinsewa.response.RequestMechResponse
import com.example.sawariapatkalinsewa.response.VehicleResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path

interface AddRequestAPI {

    @POST("request/insert/{clusername}")
    suspend fun requestmech(
        @Header("Authorization") token : String,
        @Path("clusername") clusername:String,
        @Body request: Request
    ): Response<RequestMechResponse>
}