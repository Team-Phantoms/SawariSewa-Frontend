package com.example.sawariapatkalinsewa.api

import com.example.sawariapatkalinsewa.entity.Request
import com.example.sawariapatkalinsewa.entity.Vehicle
import com.example.sawariapatkalinsewa.response.DeleteBusinessResponse
import com.example.sawariapatkalinsewa.response.RequestMechResponse
import com.example.sawariapatkalinsewa.response.VehicleResponse
import retrofit2.Response
import retrofit2.http.*

interface AddRequestAPI {

    @POST("request/insert/{clusername}")
    suspend fun requestmech(
        @Header("Authorization") token : String,
        @Path("clusername") clusername:String,
        @Body request: Request
    ): Response<RequestMechResponse>

    @DELETE("accept/delete/{id}")
    suspend fun deleteReq(
            @Header("Authorization") token : String,
            @Path("id") id : String
    ):Response<DeleteBusinessResponse>
}