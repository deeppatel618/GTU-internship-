package com.example.gtustudentportal.retrofit

import com.google.gson.Gson
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


object RetrofitClient {
    private var retrofit: Retrofit?=null;
    fun getclient(baseUrl: String): Retrofit{
        if(retrofit==null)
        {
            retrofit= Retrofit.Builder().baseUrl(baseUrl).addConverterFactory(GsonConverterFactory.create()).build()
        }
        return  retrofit!!
    }
}