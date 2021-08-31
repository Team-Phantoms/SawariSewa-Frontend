package com.example.sawariapatkalinsewa.repository

import com.example.sawariapatkalinsewa.api.AddRequestAPI
import com.example.sawariapatkalinsewa.api.MyApiRequest
import com.example.sawariapatkalinsewa.api.ServiceBuilder
import com.example.sawariapatkalinsewa.entity.Request
import com.example.sawariapatkalinsewa.response.DeleteBusinessResponse
import com.example.sawariapatkalinsewa.response.RequestMechResponse

class RequestMechRepository : MyApiRequest() {
    private val requestAPI = ServiceBuilder.buildService(AddRequestAPI::class.java)

    suspend fun requestMech(request: Request) : RequestMechResponse {
        return apiRequest {
            requestAPI.requestmech(
                    ServiceBuilder.token!!,
                    ServiceBuilder.username!!,
                    request
            )
        }
    }

    suspend fun deleteRequest(id :String): DeleteBusinessResponse {
        return apiRequest {
            requestAPI.deleteReq(
                    ServiceBuilder.token!!,id
            )
        }
    }

}