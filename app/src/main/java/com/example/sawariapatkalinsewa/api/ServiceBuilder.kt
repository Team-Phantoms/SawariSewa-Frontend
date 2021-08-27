package com.example.sawariapatkalinsewa.api

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ServiceBuilder {
   //private const val BASE_URL = "http://172.25.0.191:90/api/sas/"
  private const val BASE_URL = "http://10.0.2.2:90/api/sas/"
//private const val BASE_URL = "http://localhost:90/api/sas/"

    var token : String? = null
    var username:String?=null


    private val okHttp = OkHttpClient.Builder()

    //Create retrofit builder
    private val retrofitBuilder = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .client(okHttp.build())

    //Create retrofit instance
    private val retrofit = retrofitBuilder.build()

    //Generic function
    fun <T> buildService(serviceType : Class<T>): T{
        return retrofit.create(serviceType)
    }
    fun loadImagePath(): String {
        val arr = BASE_URL.split("/").toTypedArray()
        return arr[0] + "/" + arr[1] + arr[2] + "/uploads/"
    }
}