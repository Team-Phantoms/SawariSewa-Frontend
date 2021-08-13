package com.example.sawariapatkalinsewa.api

import com.example.sawariapatkalinsewa.entity.Business
import com.example.sawariapatkalinsewa.entity.Vehicle
import com.example.sawariapatkalinsewa.response.AddBusinessResponse
import com.example.sawariapatkalinsewa.response.GetFeedbackResponse
import com.example.sawariapatkalinsewa.response.GetVehicleResponse
import com.example.sawariapatkalinsewa.response.VehicleResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path

interface VehicleAPI {

    @POST("vehicle/web")
    suspend fun registerVehicle(
            @Header("Authorization") token : String,
            @Body vehicle:Vehicle
    ): Response<VehicleResponse>

    @POST("vehicle/show")
    suspend fun getVehicle(
            @Header("Authorization") token : String,
    ):Response<GetVehicleResponse>
}