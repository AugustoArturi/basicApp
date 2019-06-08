package com.fiuba.digitalmd.Farmacia

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast

import com.fiuba.digitalmd.Models.InfoActual
import com.fiuba.digitalmd.Models.Receta
import com.fiuba.digitalmd.Paciente.ItemReceta
import com.fiuba.digitalmd.R
import com.fiuba.digitalmd.SignInActivity
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.activity_farmacia_landing.*
import kotlinx.android.synthetic.main.activity_vender_receta.*

class VenderRecetaActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_vender_receta)
        btnBuscarReceta.setOnClickListener {
            if (camposValidos())
                mostrarReceta()
        }
    }

    private fun camposValidos(): Boolean {
        if (et_idReceta.text.toString().isEmpty()) {
            et_idReceta.error = "Por favor ingresa el id de la receta"
            et_idReceta.requestFocus()
            return false
        } else return true
    }

    override fun onBackPressed() {
        startActivity(Intent(baseContext, FarmaciaLandingActivity::class.java))
    }

    private fun mostrarReceta() {
        val receta = et_idReceta.text.toString()
        val adapter = GroupAdapter<ViewHolder>()
        val database = FirebaseDatabase.getInstance().getReference("/recetas/idRecetas/${receta}")

        database.addListenerForSingleValueEvent(object : ValueEventListener {

            override fun onCancelled(p0: DatabaseError) {
            }

            override fun onDataChange(p0: DataSnapshot) {

                if (!p0.exists()) {
                    Toast.makeText(baseContext, "La receta ingresada no existe", Toast.LENGTH_SHORT).show()

                } else {
                        val receta = p0.getValue(Receta::class.java)
                        adapter.add(ItemReceta(receta))
                        rvReceta.adapter = adapter

                        val ref = FirebaseDatabase.getInstance().getReference("/recetas/${InfoActual.getFarmaciaActual().cuit}/${receta!!.recetaID}")
                        ref.setValue(receta)
                }
            }
        })
    }
}
