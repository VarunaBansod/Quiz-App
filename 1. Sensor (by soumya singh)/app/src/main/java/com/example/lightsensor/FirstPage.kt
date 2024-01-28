package com.example.lightsensor

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper

class FirstPage : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_first_page)
        supportActionBar?.hide() //to hide action bar where you get to see back arrow or left top
        //to show splash screen
        Handler(Looper.getMainLooper()).postDelayed({
            val i= Intent(this, MainActivity::class.java)
            startActivity(i)
            finish()
        },3000)
//        Handler().postDelayed({
//            val i= Intent(this, MainActivity::class.java)
//            startActivity(i)
//            finish()
//        },3000)
    }
}