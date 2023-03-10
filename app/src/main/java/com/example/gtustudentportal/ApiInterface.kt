package com.example.gtustudentportal

import com.example.gtustudentportal.model.userData
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface ApiInterface {
    @FormUrlEncoded
    @POST("getUser.php")
    fun getUser(@Field("Eno") Eno: String,@Field("pwd") pwd: String
    ):retrofit2.Call<List<userData>>
}