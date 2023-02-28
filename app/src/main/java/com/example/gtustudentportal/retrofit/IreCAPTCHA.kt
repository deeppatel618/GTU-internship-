package com.example.gtustudentportal.retrofit

import com.example.gtustudentportal.model.Myresponse
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST
import java.net.CacheResponse

interface IreCAPTCHA {
    @FormUrlEncoded
    @POST("google recaptcha")
    fun validate(@Field("recaptcha-response") response: String): Call<Myresponse>
}