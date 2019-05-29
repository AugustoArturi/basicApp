package com.fiuba.digitalmd

import android.annotation.SuppressLint
import android.content.Intent

import android.os.Bundle
import android.util.Patterns
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_signin_user.*

class SignIn_userActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signin_user)


        btnSignUp.setOnClickListener {
            //startActivity(Intent(this, SignUpActivity::class.java))

        }
        btnForgot.setOnClickListener {
            //startActivity(Intent(this, ForgotActivity::class.java))
        }

        btnSignIn.setOnClickListener{
            confirmSignIn()
            startActivity(Intent(this, SwipeActivity::class.java))

        }

        btnSignUp.setOnClickListener {
            startActivity(Intent(this, SignUp_userActivity::class.java))
        }



    }

    @SuppressLint("NewApi")
    private fun confirmSignIn() {

        if (etEmail.text.toString().isEmpty()) {
            etEmail.error = "Plase enter your email"
            etEmail.requestFocus()
            return
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(etEmail.text.toString()).matches()) {
            etEmail.error = "Plase enter a valid email"
            etEmail.requestFocus()
            return
        }

        if (etPassword.text.toString().isEmpty()) {
            etPassword.error = "Plase enter your password"
            etPassword.requestFocus()
            return
        }
    }
}
