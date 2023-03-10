package com.example.gtustudentportal

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.cardview.widget.CardView
import com.example.gtustudentportal.model.userData
import retrofit2.*
import retrofit2.converter.gson.GsonConverterFactory

class tempView : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_temp_view)


        var personal_info= findViewById<CardView>(R.id.personal_info)
        personal_info.setOnClickListener{
            Toast.makeText(this, "Clickedd", Toast.LENGTH_SHORT).show()
        }


    }
}