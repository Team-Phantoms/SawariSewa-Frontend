package com.example.sawariapatkalinsewa.repository

import com.example.sawariapatkalinsewa.api.MyApiRequest
import com.example.sawariapatkalinsewa.api.ServiceBuilder
import com.example.sawariapatkalinsewa.entity.clientWorkHistory
import com.example.sawariapatkalinsewa.entity.workHistory
import com.example.sawariapatkalinsewa.response.DeleteBusinessResponse
import com.example.sawariapatkalinsewa.response.workHistoryResponse

class clientworkHistoryRepository: MyApiRequest() {
    private val clientworkhistoryAPI = ServiceBuilder.buildService(com.example.sawariapatkalinsewa.api.clientworkhistoryAPI::class.java)

    suspend fun insertclientWork(clientWorkHistory: clientWorkHistory) : workHistoryResponse {
        return apiRequest {
            clientworkhistoryAPI.insertclientWork(
                ServiceBuilder.token!!,
                clientWorkHistory
            )
        }
    }

    suspend fun getDriver(): workHistoryResponse {
        return apiRequest {
            clientworkhistoryAPI.getClientDriver(
                ServiceBuilder.token!!
            )
        }
    }
    suspend fun getClient(): workHistoryResponse {
        return apiRequest {
            clientworkhistoryAPI.getClient(
                ServiceBuilder.token!!
            )
        }
    }
    suspend fun deleteRequest(id :String): DeleteBusinessResponse {
        return apiRequest {
            clientworkhistoryAPI.deleteReq(
                id
            )
        }
    }

}