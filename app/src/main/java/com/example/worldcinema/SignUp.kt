package com.example.worldcinema

import android.app.DownloadManager
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.core.net.toUri
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
}