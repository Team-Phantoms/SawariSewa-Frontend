package com.example.sawariapatkalinsewa.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.sawariapatkalinsewa.entity.Business
import com.example.sawariapatkalinsewa.entity.Feedback
import com.example.sawariapatkalinsewa.entity.Vehicle
import com.example.sawariapatkalinsewa.entity.client

@Dao
interface VehicleDAO {

    @Insert
    suspend fun insertVehicle(vehicle:ArrayList<Vehicle>)


    @Query("SELECT * FROM Vehicle")
    suspend fun getVehicle() : MutableList<Vehicle>
}