package com.example.gtustudentportal

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.example.gtustudentportal.model.Myresponse
import com.example.gtustudentportal.model.userData
import com.example.gtustudentportal.retrofit.IreCAPTCHA
import com.example.gtustudentportal.retrofit.RetrofitClient
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.common.api.CommonStatusCodes
import com.google.android.gms.safetynet.SafetyNet
import com.google.android.gms.safetynet.SafetyNetApi
import dmax.dialog.SpotsDialog
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import kotlin.math.log
import kotlin.random.Random

class LoginActivity : AppCompatActivity() {

    companion object{
        private val SAFETY_NET_API_SITE_KEY ="6Ld-wr0kAAAAAFIg0mSYgJrlTRhuHWID-y3itUmh"
    }

    private val api:IreCAPTCHA
        get() = RetrofitClient.getclient("https://gtustudentportal.000webhostapp.com/").create(IreCAPTCHA::class.java)

        lateinit var mService:IreCAPTCHA
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login2)

        mService = api

        var submit= findViewById<Button>(R.id.submit_loginPage)
        var et_eno=findViewById<EditText>(R.id.et_eno)
        var et_pwd=findViewById<EditText>(R.id.et_pwd)

        submit.setOnClickListener {

            val et_eno:String=et_eno.text.toString()
            val et_pwd:String=et_pwd.text.toString()

            // Calling API
            var retrofit= Retrofit.Builder().baseUrl("https://gtustudentportal.000webhostapp.com")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(ApiInterface::class.java)
            var result = retrofit.getUser(et_eno,et_pwd)

            result.enqueue(object: Callback<List<userData>?> {
                override fun onResponse(
                    call: Call<List<userData>?>,
                    response: Response<List<userData>?>
                ) {
                    val responseUserData:userData=response.body()!!.get(0);
                    // userData will indicate id =1 if credentials are correct
                    if(responseUserData.id==1)
                    {
                        //if correct then googleRecaptcha will be called and after verifying it will be directed to next page
                        SafetyNet.getClient(this@LoginActivity)
                            .verifyWithRecaptcha(SAFETY_NET_API_SITE_KEY)
                            .addOnSuccessListener { response ->
                                if(!response.tokenResult?.isEmpty()!!)
                                {
                                    verifyToKenOnServer(response.tokenResult!!)
                                }
                            }
                            .addOnFailureListener{ e ->
                                if(e is ApiException)
                                {
                                    Log.d("EDMERROR","Error: "+ CommonStatusCodes.getStatusCodeString(e.statusCode))
                                }
                                else
                                {
                                    Log.d("EDMERROR","UNK ERROR ")
                                }
                            }
                    }
                    else{
                        Toast.makeText(applicationContext, "Invalid crendetials "+responseUserData.id, Toast.LENGTH_SHORT).show()
                    }

                }

                override fun onFailure(call: Call<List<userData>?>, t: Throwable) {
                    Toast.makeText(applicationContext, "!Error! "+ t.toString() , Toast.LENGTH_LONG).show()
                    Log.d("Error "," "+t.toString())
                }
            })
        }
    }


    //Google recaptcha
    private fun verifyToKenOnServer(response: String) {

        // dialog box
        val dialog = SpotsDialog(this@LoginActivity)
        dialog.show()
        dialog.setMessage("Please wait..")
        mService.validate(response)
            .enqueue(object :Callback<Myresponse>{
                override fun onResponse(call: Call<Myresponse>, response: Response<Myresponse>) {
                    dialog.dismiss()
                    if(response!!.body()!!.success){
                        Toast.makeText(this@LoginActivity, "comment posted", Toast.LENGTH_SHORT).show()
                        Log.d("Success","Success")
                        val intent= Intent(this@LoginActivity,MainPage::class.java)
                        startActivity(intent)
                    }
                    else{
                        Toast.makeText(this@LoginActivity, response.body()!!.message, Toast.LENGTH_SHORT).show()
                        Log.d("Fail","Fail")
                    }
                }
                override fun onFailure(call: Call<Myresponse>, t: Throwable) {
                    dialog.dismiss()
                    Log.d("EMerror",t.message!!)
                }
            })
    }
}