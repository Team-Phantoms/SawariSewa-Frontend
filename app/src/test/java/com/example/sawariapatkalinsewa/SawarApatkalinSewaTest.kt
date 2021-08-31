package com.example.sawariapatkalinsewa

import com.example.sawariapatkalinsewa.api.ServiceBuilder
import com.example.sawariapatkalinsewa.entity.*
import com.example.sawariapatkalinsewa.repository.*
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Test

class SawarApatkalinSewaTest {
    private lateinit var customerRepository: CustomerRepository
    private lateinit var mechanicRepository: MechanicRepository
    private lateinit var vehicleRepository: VehicleRepository
    private lateinit var feedBackRepository: FeedBackRepository
    private lateinit var businessRepository: BusinessRepository
    private lateinit var requestMechRepository: RequestMechRepository

    @Test
    fun checkLogin()= runBlocking {
        customerRepository= CustomerRepository()
        val response=customerRepository.loginUser("mansur12","12345")
        val expectedResult= true
        val actualResult=response.success
        Assert.assertEquals(expectedResult,actualResult)
    }
    @Test
    fun insertUser()= runBlocking {
        val mechanic =
            Mechanic(mechfname = "ram", mechlname = "shyam", mechemail = "hari", mechusername = "kisn",
                mechvechtype="Two Wheelers",mechaddress="Bhaktapur",mechPhone="98415255",mechcitizenship="2555",
                mechworkplace="Kathmandu",mechPANnum="8888",
                mechpassword = "123456")
        mechanicRepository= MechanicRepository()
        val response=mechanicRepository.registerMechanic(mechanic)
        val expectedResult= true
        val actualResult=response.success
        Assert.assertEquals(expectedResult,actualResult)

    }
    @Test
    fun insertVehicle()= runBlocking {
        customerRepository= CustomerRepository()
        vehicleRepository= VehicleRepository()
        val vehicle=
            Vehicle(vechbrand = " ",vechmodel="vechmodel",vechplatenum = "vechplatenum",clusername="mansur")
        ServiceBuilder.token="Bearer " +customerRepository.loginUser("mansur12","12345").token
        ServiceBuilder.username=customerRepository.loginUser("mansur12","12345").data
        val response=vehicleRepository.insertvehicle(vehicle)
        val expectedResult= true
        val actualResult=response.success
        Assert.assertEquals(expectedResult,actualResult)

    }

    @Test
    fun getClient()= runBlocking {
        customerRepository= CustomerRepository()
        ServiceBuilder.token="Bearer " +customerRepository.loginUser("mansur12","12345").token
        ServiceBuilder.username=customerRepository.loginUser("mansur12","12345").data
        val response=customerRepository.getcustomer()
        val expectedResult= true
        val actualResult=response.success
        Assert.assertEquals(expectedResult,actualResult)

    }
    @Test
    fun postfeedback()= runBlocking {
        customerRepository= CustomerRepository()
        feedBackRepository= FeedBackRepository()
        val feedback= Feedback(
            clusername="clusername",
            clemail="clemail",
            mechusername="mechusername",
            message="message",
        )
        ServiceBuilder.token="Bearer " +customerRepository.loginUser("mansur12","12345").token
        ServiceBuilder.username=customerRepository.loginUser("mansur12","12345").data
        val response=feedBackRepository.postfeedback(feedback)
        val expectedResult= true
        val actualResult=response.success
        Assert.assertEquals(expectedResult,actualResult)

    }

    @Test
    fun insertBusiness()= runBlocking {
        mechanicRepository= MechanicRepository()
        businessRepository= BusinessRepository()
        val business = Business(
            fullname = "fullname",
            phone = "phone",
            gender = "gender",
            address = "address",
            lat = "lat",
            long = "long",
            mechusername ="username"


        )
        ServiceBuilder.token="Bearer " +mechanicRepository.loginMech("tttt","12345").token
        val response=businessRepository.registerBusiness(business)
        val expectedResult= true
        val actualResult=response.success
        Assert.assertEquals(expectedResult,actualResult)

    }

    @Test
    fun insertRequest()= runBlocking {
        customerRepository= CustomerRepository()
        requestMechRepository= RequestMechRepository()
        val request = Request(
            problemtype = "problemtype",
            vechbrand = "vechbrand",
            vechmodel = "vechmodel",
            vechplatenum = "vechplatenum",
            address = "address",
            lat = "lat",
            long = "long",
            clusername = "ServiceBuilder"


        )
        ServiceBuilder.token="Bearer " +customerRepository.loginUser("mansur12","12345").token
        ServiceBuilder.username=customerRepository.loginUser("mansur12","12345").data
        val response=requestMechRepository.requestMech(request)
        val expectedResult= true
        val actualResult=response.success
        Assert.assertEquals(expectedResult,actualResult)

    }



}