package com.example.sawariapatkalinsewa.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.sawariapatkalinsewa.entity.Business
import com.example.sawariapatkalinsewa.entity.Feedback

@Dao
interface FeedBackDAO {
    @Insert
    suspend fun postfeedback(feedback:ArrayList<Feedback> )

    @Query("SELECT * FROM Feedback")
    suspend fun getfeedback() : MutableList<Feedback>
}