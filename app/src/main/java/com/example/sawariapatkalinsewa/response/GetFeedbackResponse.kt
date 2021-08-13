package com.example.sawariapatkalinsewa.response

import com.example.sawariapatkalinsewa.entity.Business
import com.example.sawariapatkalinsewa.entity.Feedback


data class GetFeedbackResponse (
    val success:Boolean?=null,
    val feed:MutableList<Feedback>

)