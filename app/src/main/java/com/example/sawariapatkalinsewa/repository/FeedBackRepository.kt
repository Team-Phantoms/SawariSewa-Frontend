package com.example.sawariapatkalinsewa.repository

import android.content.Context
import com.example.sawariapatkalinsewa.api.BusinessAPI
import com.example.sawariapatkalinsewa.api.FeedbackAPI
import com.example.sawariapatkalinsewa.api.MyApiRequest
import com.example.sawariapatkalinsewa.api.ServiceBuilder
import com.example.sawariapatkalinsewa.db.customerDB
import com.example.sawariapatkalinsewa.entity.Business
import com.example.sawariapatkalinsewa.entity.Feedback
import com.example.sawariapatkalinsewa.response.AddBusinessResponse
import com.example.sawariapatkalinsewa.response.AddFeedbackResponse
import com.example.sawariapatkalinsewa.response.GetFeedbackResponse

class FeedBackRepository:MyApiRequest() {
    private val feedbackAPI = ServiceBuilder.buildService(FeedbackAPI::class.java)

    suspend fun postfeedback(feedback: Feedback) : AddFeedbackResponse {
        return apiRequest {
            feedbackAPI.postfeedback(
                    ServiceBuilder.token!!,
                    feedback
            )
        }
    }

    suspend fun getfeedback():GetFeedbackResponse{
        return apiRequest {
            feedbackAPI.getfeedback(ServiceBuilder.token!!)
        }

    }
}