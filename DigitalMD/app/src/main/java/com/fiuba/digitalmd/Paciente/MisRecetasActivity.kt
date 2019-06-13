package com.fiuba.digitalmd.Paciente

import android.os.Bundle
import com.fiuba.digitalmd.Models.InfoActual
import com.fiuba.digitalmd.Models.Receta
import com.fiuba.digitalmd.R
import com.fiuba.digitalmd.SignedInActivity
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

class MisRecetasActivity : SignedInActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mis_recetas)
        setSupportActionBar(toolbar)
        cargarRecetasDeFirebase()
    }

    private fun cargarRecetasDeFirebase() {
        val adapter = GroupAdapter<ViewHolder>()
        val database = FirebaseDatabase.getInstance().getReference("/recetas")
            .orderByChild("dniPaciente").equalTo(InfoActual.getUsuarioActual().dni)

        database.addListenerForSingleValueEvent(object : ValueEventListener {

            override fun onCancelled(p0: DatabaseError) {
            }

            override fun onDataChange(p0: DataSnapshot) {

                if (!p0.exists()) {
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

