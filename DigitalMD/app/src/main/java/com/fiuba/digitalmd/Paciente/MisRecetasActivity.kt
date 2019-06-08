package com.fiuba.digitalmd.Paciente

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.fiuba.digitalmd.Medico.Vaci
import com.fiuba.digitalmd.Models.InfoActual
import com.fiuba.digitalmd.Models.Receta
import com.fiuba.digitalmd.Models.User
import com.fiuba.digitalmd.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.Item
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.activity_hacer_receta.*
import kotlinx.android.synthetic.main.activity_mis_recetas.*
import kotlinx.android.synthetic.main.receta_row.view.*

class MisRecetasActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mis_recetas)
        setSupportActionBar(toolbar)
        leerUsuarioDeFirebase()

    }

    private fun leerUsuarioDeFirebase() {
        val uid = FirebaseAuth.getInstance().uid
        val ref = FirebaseDatabase.getInstance().getReference("/signup/$uid")

        ref.addListenerForSingleValueEvent(object : ValueEventListener {

            override fun onCancelled(p0: DatabaseError) {
            }

            override fun onDataChange(p0: DataSnapshot) {

                val user = p0.getValue(User::class.java)
                InfoActual.setUsuarioActual(user!!)
                Toast.makeText(baseContext, "Usuario leido", Toast.LENGTH_SHORT).show()
                cargarRecetasDeFirebase()
            }
        })

    }

    private fun cargarRecetasDeFirebase() {
        val adapter = GroupAdapter<ViewHolder>()
        val database = FirebaseDatabase.getInstance().getReference("/recetas/${InfoActual.getUsuarioActual().dni}")

        database.addListenerForSingleValueEvent(object : ValueEventListener {

            override fun onCancelled(p0: DatabaseError) {
            }

            override fun onDataChange(p0: DataSnapshot) {

                if (!p0.exists()) {
                    //adapter.add(Vaci())
                    //rvMisRecetasPaciente.adapter = adapter
                    //finish()
                } else {

                    p0.children.forEach {
                        val receta = it.getValue(Receta::class.java)
                        adapter.add(ItemReceta(receta))
                    }

                    rvMisRecetasPaciente.adapter = adapter
                }
            }
        })
    }
}

class ItemReceta(val receta: Receta?) : Item<ViewHolder>() {

    override fun getLayout(): Int {
        return R.layout.receta_row
    }

    override fun bind(viewHolder: ViewHolder, position: Int) {

        viewHolder.itemView.dniPacientebox.text = "DNI Paciente: " + receta!!.dniPaciente

        viewHolder.itemView.obrasocialbox.text ="Obra social: " + receta.obrasocial
        viewHolder.itemView.diagnosticobox.text = "Diagnostico paciente: " +receta.diagnostico

        viewHolder.itemView.farmacobox.text = "Farmaco dado: " +receta!!.farmaco
        viewHolder.itemView.cantidadbox.text = "Cantidad: " +receta.cantidadFarmaco

        viewHolder.itemView.consumobox.text = "Modo de consumo: " +receta!!.modoConsumo
        viewHolder.itemView.lugarbox.text = "Se receto para: " +receta.lugar
        viewHolder.itemView.fechabox.text = "Fecha: " +receta!!.fecha
        viewHolder.itemView.idrecetabox.text= "ID receta: " + receta!!.recetaID
        viewHolder.itemView.matriculabox.text = "Matricula medico: " + receta!!.matricula



    }

}

