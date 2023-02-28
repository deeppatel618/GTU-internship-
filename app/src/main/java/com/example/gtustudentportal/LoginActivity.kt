package com.example.gtustudentportal

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import kotlin.math.log
import kotlin.random.Random

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {


        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login2)

        var submit= findViewById<Button>(R.id.submit_loginPage)
        var captchaTv=findViewById<TextView>(R.id.captchaTv);
        captchaTv.text=captcha();
        Toast.makeText(this, "${captchaTv.text}", Toast.LENGTH_SHORT).show()

        submit.setOnClickListener {
            captcha();
            val intent= Intent(this@LoginActivity,MainPage::class.java)
            startActivity(intent);
        }

    }
    fun captcha() : String{
        // Create a random list with 4 values of 0 and 1
        val randomValues = List(4)
        {
            Random(System.currentTimeMillis()).nextInt(0, 2) }

        var captchaString:String="";
        for (i in randomValues)
        {
            //0 indicates that there is an alphabet
            if(i==0)
            {
                captchaString+=('A'..'Z').random();
            }
            //1 indicates that there is number
            else{
                captchaString+=('1'..'9').random();
            }
//            captchaString+=listOf(('0'..'9'), ('a'..'z'), ('A'..'Z')).flatten().random()
        }
        //var rand =

        return captchaString;
    }
    override fun onStart() {

        super.onStart()
    }

    override fun onPause() {

        super.onPause()
    }
    override fun onAttachedToWindow() {

        super.onAttachedToWindow()
    }
    override fun onRestart() {

        super.onRestart()
    }
}