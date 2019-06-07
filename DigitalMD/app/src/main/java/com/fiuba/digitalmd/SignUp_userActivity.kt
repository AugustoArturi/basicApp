package com.fiuba.digitalmd

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_sign_up_user.*

class SignUp_userActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up_user)


        btnSignUpConfirm.setOnClickListener {
            startActivity(Intent(this, SignInActivity::class.java))
        }
    }
}
