package com.example.sawariapatkalinsewa.repository

import com.example.sawariapatkalinsewa.api.CustomerAPI
import com.example.sawariapatkalinsewa.api.MyApiRequest
import com.example.sawariapatkalinsewa.api.ServiceBuilder

import com.example.sawariapatkalinsewa.entity.client
import com.example.sawariapatkalinsewa.response.GetClientResponse
import com.example.sawariapatkalinsewa.response.ImageResponse
import com.example.sawariapatkalinsewa.response.LoginResponse
import okhttp3.MultipartBody

class CustomerRepository :MyApiRequest(){
    private val custAPI = ServiceBuilder.buildService(CustomerAPI::class.java)

    suspend fun registerCustomer(Client: client) : LoginResponse {
        return apiRequest {
            custAPI.registerUser(Client)
        }
    }
    suspend fun loginUser(clusername : String, clpassword : String): LoginResponse{
        return apiRequest {
            custAPI.checkUser(clusername, clpassword)
        }
    }


    suspend fun getcustomer(): GetClientResponse{
        return apiRequest {
            custAPI.getclient(
                    ServiceBuilder.token!!,
                    ServiceBuilder.username!!
            )
        }
    }
    suspend fun uploadImage(body: MultipartBody.Part)
            : ImageResponse {
        return apiRequest {
            custAPI.uploadImage(ServiceBuilder.token!!, ServiceBuilder.username!!, body)
        }
    }

    suspend fun logout() : LoginResponse{
        return apiRequest {
            custAPI.logout(ServiceBuilder.token!!)
        }
    }

}