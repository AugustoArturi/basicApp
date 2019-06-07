package com.fiuba.digitalmd

import android.annotation.SuppressLint
import android.content.Intent

import android.os.Bundle
import android.util.Log
import android.util.Patterns
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.fiuba.digitalmd.Medico.SwipeActivity
import com.fiuba.digitalmd.Paciente.MisDiagnosticosActivity
import com.google.android.gms.common.util.ArrayUtils.contains
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.activity_signin_user.*

class SignInActivity : AppCompatActivity() {
    private lateinit var mAuth: FirebaseAuth
    private lateinit var mAuthDatabase: FirebaseDatabase
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signin_user)
        mAuth = FirebaseAuth.getInstance()

        btnSignUp.setOnClickListener {
            startActivity(Intent(this, ElegirUsuarioActivity::class.java))

        }
        btnForgot.setOnClickListener {
            //startActivity(Intent(this, ForgotActivity::class.java))
        }

        btnSignIn.setOnClickListener{
            confirmSignIn()


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


        mAuth.signInWithEmailAndPassword(etEmail.text.toString(), etPassword.text.toString())
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    Toast.makeText(baseContext,"Bienvenido a DIGITAL MD", Toast.LENGTH_SHORT).show()
                    val uid = FirebaseAuth.getInstance().uid
                    val ref = FirebaseDatabase.getInstance().getReference("/signup/$uid")
                    ref.addListenerForSingleValueEvent(object : ValueEventListener {

                        override fun onCancelled(p0: DatabaseError) {
                        }

                        override fun onDataChange(p0: DataSnapshot) {
                            val data = p0.value.toString()
                            if (data.contains("paciente", ignoreCase = true))
                                startActivity(Intent(baseContext, MisDiagnosticosActivity::class.java))
                            if (data.contains("medico", ignoreCase = true))
                                startActivity(Intent(baseContext, SwipeActivity::class.java))

                        }
                    })


                } else {
                    Toast.makeText(baseContext,"Usuario o contraseña invalidos", Toast.LENGTH_SHORT).show()
                }
            }
    }
}
