package com.example.sawariapatkalinsewa.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.sawariapatkalinsewa.dao.BusinessDAO
import com.example.sawariapatkalinsewa.dao.FeedBackDAO
import com.example.sawariapatkalinsewa.dao.VehicleDAO
import com.example.sawariapatkalinsewa.entity.Business
import com.example.sawariapatkalinsewa.entity.Feedback
import com.example.sawariapatkalinsewa.entity.Vehicle

//import com.example.sawariapatkalinsewa.entity.Customer

@Database(
        entities = [(Business::class),(Feedback::class),(Vehicle::class)],
        version = 15,
        exportSchema = false
)

abstract class customerDB : RoomDatabase() {
  abstract fun getBusinessDAO():BusinessDAO
  abstract fun getFeedbackDAO():FeedBackDAO
  abstract fun getVehicleDAO():VehicleDAO


    companion object{
        @Volatile
        private var instance:customerDB?=null

        fun getInstance(context: Context): customerDB {
            if (instance == null) {
                synchronized(customerDB::class) {
                    instance = buildDatabase(context)
                }
            }
            return instance!!
        }

        private fun buildDatabase(context: Context) =
                Room.databaseBuilder(
                        context.applicationContext,
                        customerDB::class.java,
                        "businessDB"
                ).fallbackToDestructiveMigration().build()
    }
    }
