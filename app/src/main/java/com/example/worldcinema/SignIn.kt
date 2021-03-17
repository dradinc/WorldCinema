package com.example.worldcinema

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import org.json.JSONObject
import java.io.IOException
import java.lang.StringBuilder

class SignIn : AppCompatActivity() {
    private var emailText : EditText? = null
    private var passwordText : EditText? = null

    // Переменная для работы с API

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)

        emailText = findViewById(R.id.EmailInput)
        passwordText = findViewById(R.id.PasswordInput)
    }

    public fun reg_onClick (view : View) {
        val regActivity = Intent(this, SignUp::class.java)
        startActivity(regActivity)
    }

    public fun auth_onClick (view : View) {
        forTest()
    }

    private fun forTest() {

    }
}