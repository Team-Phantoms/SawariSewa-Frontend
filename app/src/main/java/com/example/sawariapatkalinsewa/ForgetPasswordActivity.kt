package com.example.sawariapatkalinsewa

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText

class ForgetPasswordActivity : AppCompatActivity() {

    private lateinit var username: EditText
    private lateinit var newPassword: EditText
    private lateinit var confirmPassword: EditText
    private lateinit var btnSearch: Button
    private lateinit var reset: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forget_password)

        username=findViewById(R.id.username)
        newPassword=findViewById(R.id.newPassword)
        confirmPassword=findViewById(R.id.confirmPassword)
        btnSearch=findViewById(R.id.btnSearch)
        reset=findViewById(R.id.reset)
    }
}