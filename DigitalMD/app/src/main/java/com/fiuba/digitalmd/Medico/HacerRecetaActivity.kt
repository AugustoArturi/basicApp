package com.fiuba.digitalmd.Medico

import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.widget.Toast
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity;
import com.fiuba.digitalmd.Models.InfoActual
import com.fiuba.digitalmd.Models.Receta
import com.fiuba.digitalmd.Models.idReceta
import com.fiuba.digitalmd.Paciente.PacienteActivity
import com.fiuba.digitalmd.R
import com.fiuba.digitalmd.ValidacionUtils
import com.fiuba.digitalmd.ValidacionUtils.validarNoVacio
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
        val matricula = InfoActual.getMedicoActual().matricula
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
        validarNoVacio(dniPacientebox, "Por favor ingresa DNI del paciente")
        validarNoVacio(obrasocialbox, "Por favor ingresa DNI del paciente")
        validarNoVacio(diagnosticobox, "Por favor ingresa el diagnostico")
        validarNoVacio(farmacobox, "Por favor ingresa el farmaco")
        validarNoVacio(cantidadbox, "Por favor ingresa cantidad del farmaco")
        validarNoVacio(consumobox, "Por favor ingresa la forma de consumo del farmaco")
        validarNoVacio(lugarbox, "Por favor ingresa el lugar donde trabajas")
        validarNoVacio(fechabox, "Por favor ingresa la fecha")
        return true

    }

}

