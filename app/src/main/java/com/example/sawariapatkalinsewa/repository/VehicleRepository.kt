package com.example.sawariapatkalinsewa.repository

import com.example.sawariapatkalinsewa.api.MyApiRequest
import com.example.sawariapatkalinsewa.api.ServiceBuilder
import com.example.sawariapatkalinsewa.api.VehicleAPI
import com.example.sawariapatkalinsewa.entity.Vehicle
import com.example.sawariapatkalinsewa.response.GetVehicleResponse
import com.example.sawariapatkalinsewa.response.VehicleResponse

class VehicleRepository : MyApiRequest() {
    private val vehicleAPI = ServiceBuilder.buildService(VehicleAPI::class.java)

    suspend fun insertvehicle(vehicle: Vehicle) : VehicleResponse {
        return apiRequest {
            vehicleAPI.registerVehicle(
                    ServiceBuilder.token!!,
                    vehicle
            )
        }
    }

    suspend fun getVehicle():GetVehicleResponse{
        return apiRequest {
            vehicleAPI.getVehicle(ServiceBuilder.token!!)
        }

    }
}