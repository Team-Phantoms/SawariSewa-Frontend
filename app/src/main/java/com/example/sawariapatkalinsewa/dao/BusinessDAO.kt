package com.example.sawariapatkalinsewa.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.sawariapatkalinsewa.entity.Business

@Dao
interface BusinessDAO {
    @Insert
    suspend fun insertData(business:ArrayList<Business> )

    @Query("SELECT * FROM Business")
    suspend fun getAllDetails() : MutableList<Business>

}