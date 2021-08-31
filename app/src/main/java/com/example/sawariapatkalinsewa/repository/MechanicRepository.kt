package com.example.sawariapatkalinsewa.repository


import com.example.sawariapatkalinsewa.api.MechanicAPI
import com.example.sawariapatkalinsewa.api.MyApiRequest
import com.example.sawariapatkalinsewa.api.ServiceBuilder
import com.example.sawariapatkalinsewa.entity.Mechanic
import com.example.sawariapatkalinsewa.response.GetClientResponse
import com.example.sawariapatkalinsewa.response.LoginResponse
import com.example.sawariapatkalinsewa.response.ViewRequestResponse

class MechanicRepository: MyApiRequest() {
    private val mechAPI = ServiceBuilder.buildService(MechanicAPI::class.java)

    suspend fun registerMechanic(mechanic: Mechanic) : LoginResponse {
        return apiRequest {
            mechAPI.registerMech(mechanic)
        }
    }
    suspend fun loginMech(mechusername : String, mechpassword : String): LoginResponse {
        return apiRequest {
            mechAPI.checkMech(mechusername, mechpassword)
        }
    }
    suspend fun logout() : LoginResponse{
        return apiRequest {
            mechAPI.logout(ServiceBuilder.token!!)
        }
    }
    suspend fun getMech(): GetClientResponse {
        return apiRequest {
            mechAPI.getMech(
                ServiceBuilder.token!!
            )
        }
    }
    suspend fun getRequest(): ViewRequestResponse {
        return apiRequest {
            mechAPI.getRequest(
                    ServiceBuilder.token!!
            )
        }
    }
}