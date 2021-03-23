package com.example.worldcinema

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import com.google.gson.Gson
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaTypeOrNull
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

    @Override
    public fun reg_onClick (view : View) {
        val regActivity = Intent(this, SignUp::class.java)
        startActivity(regActivity)
    }

    @Override
    public fun auth_onClick (view : View) {
        if (emailText?.text.toString().checkEmail() and (passwordText?.text?.length!=0)) {
            findViewById<ProgressBar>(R.id.progressBar).visibility = View.VISIBLE
            Login()
        }
        else {
            if (!emailText?.text.toString().checkEmail()) emailText?.error = "Не верный формат E-mail адреса"
            if (emailText?.text?.length == 0) emailText?.error = "Поле не заполнено"
            if (passwordText?.text?.length == 0) passwordText?.error = "Поле не заполнено"
        }
    }

    @Override
    private fun String.checkEmail() : Boolean {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(this).matches()
    }

    @Override
    private fun Login() {
        try {
            val client = OkHttpClient()
            val gson = Gson()

            val email = findViewById<EditText>(R.id.EmailInput).text
            val password = findViewById<EditText>(R.id.PasswordInput).text

            val requestBody  = RequestBody.create("application/json; charset=utf-8".toMediaTypeOrNull(),
                    "{\"email\":\"$email\"," +
                            "\"password\":\"$password\"}")

            val url = BuildConfig.Server

            val request = Request.Builder()
                    .url("$url/auth/login")
                    .post(requestBody)
                    .build()

            client.newCall(request).enqueue(object : Callback {

                override fun onFailure(call: Call, e: IOException) {
                    var result : String = e.toString()
                    runOnUiThread {
                        Toast.makeText(this@SignIn, "Ошибка при авторизации 1", Toast.LENGTH_LONG).show()
                        findViewById<ProgressBar>(R.id.progressBar).visibility = View.INVISIBLE
                    }
                }

                override fun onResponse(call: Call, response: Response) {
                    var result : String = response.toString()
                    if (response.code == 200) {runOnUiThread {

                        val responseBody = response.body?.string()
                        val loginUser : Login_GET = gson.fromJson(responseBody, Login_GET::class.java)
                        var token = loginUser.token

                        val openMain = Intent(this@SignIn, Main::class.java)
                        startActivity(openMain)
                        finish()
                    }}
                    else if (response.code == 400) { runOnUiThread{
                        Toast.makeText(this@SignIn, "Ошибка при авторизации 2", Toast.LENGTH_LONG).show()
                        findViewById<ProgressBar>(R.id.progressBar).visibility = View.INVISIBLE
                    }}
                }
            })
        }
        catch (e : IOException) {
            Toast.makeText(this@SignIn, "Непредвиденная ошибка", Toast.LENGTH_LONG).show()
        }
    }
}