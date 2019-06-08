package com.fiuba.digitalmd.Medico

import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.widget.Toast
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity;
import com.fiuba.digitalmd.Models.Receta
import com.fiuba.digitalmd.Models.idReceta
import com.fiuba.digitalmd.Paciente.PacienteActivity
import com.fiuba.digitalmd.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.activity_hacer_receta.*


class HacerRecetaActivity : AppCompatActivity() {
    private lateinit var mAuth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_hacer_receta)
        setSupportActionBar(toolbar)

        mAuth = FirebaseAuth.getInstance()


        btnCrearReceta.setOnClickListener {
            if(validarCampos())
                subirRecetaAFirebase()
        }
    }

    private fun subirRecetaAFirebase() {
         val matricula = matriculabox.text.toString()
        val dniPaciente = dniPacientebox.text.toString()
        val obrasocial = obrasocialbox.text.toString()
        val diagnostico = diagnosticobox.text.toString()
        val farmaco = farmacobox.text.toString()
        val cantidadFarmaco = cantidadbox.text.toString()
        val modoConsumo = consumobox.text.toString()
        val lugar = lugarbox.text.toString()
        val fecha = fechabox.text.toString()
        val database = FirebaseDatabase.getInstance().getReference("/idReceta")

        database.addListenerForSingleValueEvent(object : ValueEventListener {

            override fun onCancelled(p0: DatabaseError) {
            }

            override fun onDataChange(p0: DataSnapshot) {
                val idReceta = p0.getValue(idReceta::class.java)
                database.child("numero").setValue(idReceta!!.numero+1)
                val receta = Receta(matricula,dniPaciente,obrasocial,diagnostico,farmaco,cantidadFarmaco,modoConsumo,lugar,fecha,idReceta.numero+1)
                val ref1 =  FirebaseDatabase.getInstance().getReference("/recetas/$dniPaciente/${idReceta.numero+1}")
                val ref2 = FirebaseDatabase.getInstance().getReference("/recetas/idRecetas/${idReceta.numero+1}")
                val ref3 = FirebaseDatabase.getInstance().getReference("/recetas/$obrasocial/${idReceta.numero+1}")
                val ref4 = FirebaseDatabase.getInstance().getReference("/recetas/$matricula/${idReceta.numero+1}")
                ref1.setValue(receta)
                ref2.setValue(receta)
                ref3.setValue(receta)
                ref4.setValue(receta)
                startActivity(Intent(baseContext, MedicoLandingActivity::class.java))
            }
        })



    }

    private fun validarCampos(): Boolean {
        if (matriculabox.text.toString().isEmpty()) {
            matriculabox.error = "Por favor ingresa tu matricula"
            matriculabox.requestFocus()
            return false
        }

        if (dniPacientebox.text.toString().isEmpty()) {
            dniPacientebox.error = "Por favor ingresa DNI del paciente"
            dniPacientebox.requestFocus()
            return false
        }
        if (obrasocialbox.text.toString().isEmpty()) {
            obrasocialbox.error = "Por favor ingresa DNI del paciente"
            obrasocialbox.requestFocus()
            return false
        }

        if (diagnosticobox.text.toString().isEmpty()) {
            diagnosticobox.error = "Por favor ingresa el diagnostico"
            diagnosticobox.requestFocus()
            return false
        }

        if (farmacobox.text.toString().isEmpty()) {
            farmacobox.error = "Por favor ingresa el farmaco"
            farmacobox.requestFocus()
            return false
        }


        if (cantidadbox.text.toString().isEmpty()) {
            cantidadbox.error = "Por favor ingresa cantidad del farmaco"
            cantidadbox.requestFocus()
            return false
        }


        if (consumobox.text.toString().isEmpty()) {
            consumobox.error = "Por favor ingresa la forma de consumo del farmaco"
            consumobox.requestFocus()
            return false
        }

        if (lugarbox.text.toString().isEmpty()) {
            lugarbox.error = "Por favor ingresa el lugar donde trabajas"
            lugarbox.requestFocus()
            return false
        }

        if (fechabox.text.toString().isEmpty()) {
            fechabox.error = "Por favor ingresa la fecha"
            fechabox.requestFocus()
            return false
        }


        return true

    }

}

