package com.example.sawariapatkalinsewa.repository

import com.example.sawariapatkalinsewa.api.MyApiRequest
import com.example.sawariapatkalinsewa.api.ServiceBuilder
import com.example.sawariapatkalinsewa.entity.Request
import com.example.sawariapatkalinsewa.entity.workHistory
import com.example.sawariapatkalinsewa.response.RequestMechResponse
import com.example.sawariapatkalinsewa.response.ViewRequestResponse
import com.example.sawariapatkalinsewa.response.workHistoryResponse

class workHistoryRepository:MyApiRequest() {
    private val workHistoryAPI = ServiceBuilder.buildService(com.example.sawariapatkalinsewa.api.workHistoryAPI::class.java)

    suspend fun insertWork(workHistory: workHistory) : workHistoryResponse {
        return apiRequest {
            workHistoryAPI.insertWork(
                    ServiceBuilder.token!!,
                    workHistory
            )
        }
    }

    suspend fun getDriver(): workHistoryResponse {
        return apiRequest {
            workHistoryAPI.getDriver(
                    ServiceBuilder.token!!
            )
        }
    }

    suspend fun getWork(): workHistoryResponse {
        return apiRequest {
            workHistoryAPI.getWork(
                    ServiceBuilder.token!!
            )
        }
    }
}