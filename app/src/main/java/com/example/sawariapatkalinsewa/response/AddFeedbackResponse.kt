package com.example.sawariapatkalinsewa.response

import com.example.sawariapatkalinsewa.entity.Feedback

data class AddFeedbackResponse (
        val success : Boolean? = null,
        val feedback: Feedback? =null,
)