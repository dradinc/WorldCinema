package com.example.worldcinema

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View

class SignIn : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)
    }

    public fun reg_onClick (view : View) {
        val regActivity = Intent(this, SignUp::class.java)
        startActivity(regActivity)
    }

    public fun auth_onClick (view : View) {
        
    }
}