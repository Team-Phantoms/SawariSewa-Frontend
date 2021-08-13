package com.example.sawariapatkalinsewa.api

import com.example.sawariapatkalinsewa.entity.Business
import com.example.sawariapatkalinsewa.entity.Feedback
import com.example.sawariapatkalinsewa.response.AddBusinessResponse
import com.example.sawariapatkalinsewa.response.AddFeedbackResponse
import com.example.sawariapatkalinsewa.response.GetBusinessResponse
import com.example.sawariapatkalinsewa.response.GetFeedbackResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path

interface FeedbackAPI {
    @POST("feedback/insert")
    suspend fun postfeedback(
            @Header("Authorization") token : String,
            @Body feedback: Feedback
    ): Response<AddFeedbackResponse>

    @POST("feedback/view")
    suspend fun getfeedback(
        @Header("Authorization") token : String
    ):Response<GetFeedbackResponse>
}