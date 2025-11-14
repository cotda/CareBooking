package com.example.carebooking.data.network
// app/src/main/java/com/example/carebooking/data/network/ApiService.kt
import com.example.carebooking.data.model.User
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiService {
    @POST("users/register")
    fun registerUser(@Body registerData: Map<String, String>): Call<ResponseBody>

    @POST("users/login")
    fun loginUser(@Body loginData: Map<String, String>): Call<ResponseBody>

    @POST("users/forgot-password")
    fun forgotPassword(@Body emailData: Map<String, String>): Call<ResponseBody>

    @POST("users/reset-password")
    fun resetPassword(@Body data: Map<String, String>): Call<ResponseBody>
}
