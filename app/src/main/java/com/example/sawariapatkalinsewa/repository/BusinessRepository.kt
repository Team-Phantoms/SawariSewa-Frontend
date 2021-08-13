package com.example.sawariapatkalinsewa.repository

import android.content.Context
import com.example.sawariapatkalinsewa.api.BusinessAPI
import com.example.sawariapatkalinsewa.api.CustomerAPI
import com.example.sawariapatkalinsewa.api.MyApiRequest
import com.example.sawariapatkalinsewa.api.ServiceBuilder
import com.example.sawariapatkalinsewa.db.customerDB
import com.example.sawariapatkalinsewa.entity.Business
import com.example.sawariapatkalinsewa.response.AddBusinessResponse
import com.example.sawariapatkalinsewa.response.DeleteBusinessResponse
import com.example.sawariapatkalinsewa.response.LoginResponse

class BusinessRepository: MyApiRequest(){
    private val businesAPI = ServiceBuilder.buildService(BusinessAPI::class.java)

    suspend fun registerBusiness(business: Business) : AddBusinessResponse {
        return apiRequest {
            businesAPI.registerBusiness(
                ServiceBuilder.token!!,
                business
            )
        }
    }

    suspend fun getBusiness(context: Context):MutableList<Business>{
        val response=apiRequest {
            businesAPI.getBusiness(ServiceBuilder.token!!)
        }
        var business= mutableListOf<Business>()
        if (response.success==true)
        {
            val data:ArrayList<Business> = response.bdata!!
            customerDB.getInstance(context).clearAllTables()

            customerDB.getInstance(context).getBusinessDAO().insertData(data)
            business=customerDB.getInstance(context).getBusinessDAO().getAllDetails()

        }
        return business
    }

    suspend fun getmechBusiness(context: Context):MutableList<Business>{
        val response=apiRequest {
            businesAPI.getmechBusiness(ServiceBuilder.token!!,ServiceBuilder.username!!)
        }
        var business= mutableListOf<Business>()
        if (response.success==true)
        {
            val data:ArrayList<Business> = response.bdata!!
            customerDB.getInstance(context).clearAllTables()

            customerDB.getInstance(context).getBusinessDAO().insertData(data)
            business=customerDB.getInstance(context).getBusinessDAO().getAllDetails()

        }
        return business
    }
    suspend fun deleteBusiness(id :String): DeleteBusinessResponse{
        return apiRequest {
            businesAPI.deletebusiness(
                ServiceBuilder.token!!,id
            )
        }
    }

    suspend fun updateBusiness(id :String, business: Business): DeleteBusinessResponse{
        return apiRequest {
            businesAPI.updateBusiness(
                ServiceBuilder.token!!, id,
                business
            )
        }
    }




}