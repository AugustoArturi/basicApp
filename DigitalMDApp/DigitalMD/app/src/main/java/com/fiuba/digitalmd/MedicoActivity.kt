package com.fiuba.digitalmd

import android.annotation.SuppressLint
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import kotlinx.android.synthetic.main.activity_dermatologia.*
import kotlinx.android.synthetic.main.activity_medico.*

class MedicoActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_medico)


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
