package com.fiuba.digitalmd.Paciente


import android.content.Intent

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

import android.util.Log
import android.util.Patterns
import android.widget.Toast

import com.fiuba.digitalmd.Models.User
import com.fiuba.digitalmd.R
import com.fiuba.digitalmd.SignInActivity
import com.fiuba.digitalmd.ValidacionUtils.validarNoVacio
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

import kotlinx.android.synthetic.main.activity_sign_up_paciente.*

import kotlinx.android.synthetic.main.activity_sign_up_user.emailbox
import kotlinx.android.synthetic.main.activity_sign_up_user.passwordbox


class SignUpPacienteActivity : AppCompatActivity() {

    private lateinit var mAuth: FirebaseAuth


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up_paciente)
        mAuth = FirebaseAuth.getInstance()
        btnSignUpPaciente.setOnClickListener {
            if(validarCampos())
                subirPacienteAFirebase()
        }
    }

    private fun subirPacienteAFirebase() {
        val name = nombrebox.text.toString()
        val apellido = apellidobox.text.toString()
        val dni =dnibox.text.toString()
        val email=emailbox.text.toString()
        val password =  passwordbox.text.toString()
        val fecha = fechabox.text.toString()
        val obrasocial =  obrasocialbox.text.toString()


        val user = User(name, apellido, dni, email, fecha, obrasocial, "paciente")
        mAuth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    subirloAFirebase(user)
                } else {
                    Toast.makeText(baseContext, "Sign up failed", Toast.LENGTH_SHORT).show()
                }
            }


    }

    private fun subirloAFirebase(user: User) {
        val uid = FirebaseAuth.getInstance().uid ?: ""
        val ref = FirebaseDatabase.getInstance().getReference("/signup/$uid")
        ref.setValue(user)
            .addOnSuccessListener {
                Log.d("SignUpActivity", "User added to database")
                startActivity(Intent(this, SignInActivity::class.java))
            }

    }


    private fun validarCampos(): Boolean {
        if (validarNoVacio(nombrebox, "Por favor ingresa tu nombre")) return false
        if (validarNoVacio(apellidobox,  "Por favor ingresa tu apellido")) return false
        if (validarNoVacio(dnibox, "Por favor ingresa tu DNI")) return false
        if (validarNoVacio(emailbox, "Por favor ingresa tu email")) return false
        if (validarNoVacio(passwordbox, "Por favor ingresa una contraseña")) return false
        if (validarNoVacio(fechabox, "Por favor ingresa tu fecha de nacimiento")) return false
        if (validarNoVacio(obrasocialbox, "Por favor ingresa tu obra social")) return false

        if (!Patterns.EMAIL_ADDRESS.matcher(emailbox.text.toString()).matches()) {
            emailbox.error = "Por favor ingresa un email valido"
            emailbox.requestFocus()
            return false
        }

        if (passwordbox.text.toString().length < 6) {
            passwordbox.error = "Por favor ingresa una contraseña al menos 6 caracteres"
            passwordbox.requestFocus()
            return false
        }

        return true
    }



}
