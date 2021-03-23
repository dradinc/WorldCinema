package com.example.worldcinema

import android.app.DownloadManager
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.core.net.toUri
import com.google.gson.Gson
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import org.json.JSONObject
import java.io.IOException
import java.lang.StringBuilder
import java.net.URL

class SignUp : AppCompatActivity() {

    // Переменные для работы с элеменатми
    private var nameText : EditText? = null
    private var lastnameText : EditText? = null
    private var emailText : EditText? = null
    private var passwordText : EditText? = null
    private var repeatpasswordText : EditText? = null

    // Переменная для работы с API

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        nameText = findViewById(R.id.NameInput)
        lastnameText = findViewById(R.id.LastNameInput)
        emailText = findViewById(R.id.EmailInput)
        passwordText = findViewById(R.id.PasswordInput)
        repeatpasswordText = findViewById(R.id.RepeatPasswordInput)
    }

    public fun auth_onClick(view : View) {
        finish()
    }

    public fun reg_onClick(view : View) {
        if ((nameText?.text?.length  != 0) and (lastnameText?.text?.length != 0) and (emailText?.text?.length != 0) and (passwordText?.text?.length != 0) and (repeatpasswordText?.text?.length != 0)) {
            if ((emailText?.text.toString().isEmailValid()) and (passwordText?.text.toString() == repeatpasswordText?.text.toString())) {
                findViewById<ProgressBar>(R.id.progressBar).visibility = View.VISIBLE
                register()
            }
            else {
                // Проверка E-mail на корректность ввода
                if (emailText?.text.toString().isEmailValid()) {
                } else emailText?.error = "Некорректно введён E-mail"

                // Проверка паролей на совпадение
                if (passwordText?.text.toString() == repeatpasswordText?.text.toString()) {}
                else repeatpasswordText?.error = "Пароли не совпадают"
            }
        }
        else {
            // Проверка поля << Имя >> на заполненость
            if (nameText?.text?.length == 0) nameText?.error = "Заполните поле"

            // Проверка поля << Фамилия >> на заполненость
            if (lastnameText?.text?.length == 0) lastnameText?.error = "Заполните поле"

            // Проверка поля << E-mail >> на заполненость
            if (emailText?.text?.length == 0) emailText?.error = "Заполните поле"

            // Проверка поля << Пароль >> на заполненость
            if (passwordText?.text?.length == 0) passwordText?.error = "Заполните поле"

            // Проверка поля << Повтор пароля >> на заполненость
            if (repeatpasswordText?.text?.length == 0) repeatpasswordText?.error = "Заполните поле"
        }
    }

    private fun String.isEmailValid() : Boolean {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(this).matches()
    }

    private fun register() {
        try {
            val client = OkHttpClient()
            val url = BuildConfig.Server

            val email = findViewById<EditText>(R.id.EmailInput).text
            val password = findViewById<EditText>(R.id.PasswordInput).text
            val firstName = findViewById<EditText>(R.id.NameInput).text
            val lastName = findViewById<EditText>(R.id.LastNameInput).text

            val requestBody = RequestBody.create("application/json; charset=utf-8".toMediaTypeOrNull(),
                    "{\"email\":\"$email\"," +
                            "\"password\":\"$password\"," +
                            "\"firstName\":\"$firstName\"," +
                            "\"lastName\":\"$lastName\"}")

            val request = Request.Builder()
                    .url("$url/auth/register")
                    .post(requestBody)
                    .build()

            client.newCall(request).enqueue(object : Callback {
                override fun onFailure(call: Call, e: IOException) {
                    var result : String = e.toString()
                    runOnUiThread {
                        Toast.makeText(this@SignUp, "Ошибка при регистрации 1", Toast.LENGTH_LONG).show()
                        findViewById<ProgressBar>(R.id.progressBar).visibility = View.INVISIBLE
                    }
                }

                override fun onResponse(call: Call, response: Response) {
                    var result : String = response.toString()
                    if (response.code == 201) { runOnUiThread {
                        Toast.makeText(this@SignUp, "Успешная регистрация", Toast.LENGTH_LONG).show()
                        finish()
                    }}
                    else if (response.code == 400) { runOnUiThread {
                        Toast.makeText(this@SignUp, "Ошибка при регистрации 2", Toast.LENGTH_LONG).show()
                        findViewById<ProgressBar>(R.id.progressBar).visibility = View.INVISIBLE
                    }}
                }

            })
        }
        catch (e : IOException) {
            Toast.makeText(this@SignUp, "Произошла непредвиденная ошибка", Toast.LENGTH_LONG).show()
            findViewById<ProgressBar>(R.id.progressBar).visibility = View.INVISIBLE
        }
    }
}