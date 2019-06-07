package com.fiuba.digitalmd.Medico

import android.app.Activity
import android.app.ProgressDialog
import android.content.Intent
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.util.Patterns
import android.widget.Toast
import androidx.annotation.RequiresApi
import com.fiuba.digitalmd.Models.Medico
import com.fiuba.digitalmd.Models.User
import com.fiuba.digitalmd.R
import com.fiuba.digitalmd.SignInActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import kotlinx.android.synthetic.main.activity_paciente.*
import kotlinx.android.synthetic.main.activity_sign_up_medico.*
import kotlinx.android.synthetic.main.activity_sign_up_paciente.*
import kotlinx.android.synthetic.main.activity_sign_up_paciente.apellidobox
import kotlinx.android.synthetic.main.activity_sign_up_paciente.emailbox
import kotlinx.android.synthetic.main.activity_sign_up_paciente.nombrebox
import kotlinx.android.synthetic.main.activity_sign_up_paciente.obrasocialbox
import kotlinx.android.synthetic.main.activity_sign_up_paciente.passwordbox
import kotlinx.android.synthetic.main.activity_sign_up_user.*
import java.util.*

class SignUpMedicoActivity : AppCompatActivity() {
    private lateinit var mAuth: FirebaseAuth
    var photoUri: Uri? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up_medico)
        mAuth = FirebaseAuth.getInstance()
        btnSignUpDoctor.setOnClickListener {
            if(validarCampos())
                subirImagenAFirebase()
        }

        btnSubirFotoMedico.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK)
            intent.type = "image/*"
            startActivityForResult(intent, 0)
        }
    }


    private fun subirDoctorAFirebase(url: String) {

        val name = nombrebox.text.toString()
        val apellido = apellidobox.text.toString()
        val matricula =matriculabox.text.toString()
        val email=emailbox.text.toString()
        val password =  passwordbox.text.toString()
        val obrasocial =  obrasocialbox.text.toString()


        val medico = Medico(name, apellido, matricula, email, obrasocial, "medico", url)
        mAuth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    subirloAFirebase(medico)
                } else {
                    Toast.makeText(baseContext, "Sign up failed", Toast.LENGTH_SHORT).show()
                }
            }


    }

    private fun subirImagenAFirebase(){

        val ref = FirebaseStorage.getInstance().getReference("/images/Medicos")
        val progressDialog = ProgressDialog(this)

        progressDialog.setTitle("Creando nuevo perfil de medico ")
        progressDialog.show()
        ref.putFile(photoUri!!)
            .addOnSuccessListener { taskSnapshot ->
                ref.downloadUrl.addOnCompleteListener { taskSnapshot ->
                    var url = taskSnapshot.result
                    Log.d("SignUp Medico", "Image added to firebase: ${url.toString()}")
                    subirDoctorAFirebase(url.toString())
                }
            }
            .addOnProgressListener { taskSnapShot ->
                val progress = 100 * taskSnapShot.bytesTransferred / taskSnapShot.totalByteCount
                progressDialog.setMessage("% ${progress}")
            }


    }

    private fun subirloAFirebase(medico: Medico) {
        val uid = FirebaseAuth.getInstance().uid ?: ""
        val ref = FirebaseDatabase.getInstance().getReference("/signup/$uid")
        ref.setValue(medico)
            .addOnSuccessListener {
                Log.d("SignUpActivity", "User added to database")
                startActivity(Intent(this, SignInActivity::class.java))
            }

    }


    private fun validarCampos(): Boolean {
        if (nombrebox.text.toString().isEmpty()) {
            nombrebox.error = "Por favor ingresa tu nombre"
            nombrebox.requestFocus()
            return false
        }

        if (apellidobox.text.toString().isEmpty()) {
            apellidobox.error = "Por favor ingresa tu apellido"
            apellidobox.requestFocus()
            return false
        }

        if (matriculabox.text.toString().isEmpty()) {
            matriculabox.error = "Por favor ingresa tu matricula"
            matriculabox.requestFocus()
            return false
        }

        if (emailbox.text.toString().isEmpty()) {
            emailbox.error = "Por favor ingresa tu email"
            emailbox.requestFocus()
            return false
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(emailbox.text.toString()).matches()) {
            emailbox.error = "Por favor ingresa un email valido"
            emailbox.requestFocus()
            return false
        }

        if (passwordbox.text.toString().isEmpty()) {
            passwordbox.error = "Por favor ingresa una contraseña"
            passwordbox.requestFocus()
            return false
        }


        if (obrasocialbox.text.toString().isEmpty()) {
            obrasocialbox.error = "Por favor ingresa tu obra social"
            obrasocialbox.requestFocus()
            return false
        }

        return true
    }

    @RequiresApi(Build.VERSION_CODES.JELLY_BEAN)
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == 0 && resultCode == Activity.RESULT_OK && data != null) {
            photoUri = data.data
            val bitmap = MediaStore.Images.Media.getBitmap(contentResolver, photoUri)
            CircleImageViewMedico.setImageBitmap(bitmap)
            btnSubirFotoMedico.background = null
            btnSubirFotoMedico.text = null


        }
    }
}
