package com.fiuba.digitalmd.Medico

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.fiuba.digitalmd.Models.InfoActual
import com.fiuba.digitalmd.Models.Receta
import com.fiuba.digitalmd.Paciente.Vac
import com.fiuba.digitalmd.R
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.Item
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.activity_hacer_receta.*
import kotlinx.android.synthetic.main.activity_ver_recetas_medico.*
import kotlinx.android.synthetic.main.receta_row.view.*

class VerRecetasMedicoActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ver_recetas_medico)
        setSupportActionBar(toolbar)
        cargarRecetasDeFirebase()
    }

    private fun cargarRecetasDeFirebase() {
        val adapter = GroupAdapter<ViewHolder>()
        val database = FirebaseDatabase.getInstance().getReference("/recetas/${InfoActual.getMedicoActual().matricula}")

        database.addListenerForSingleValueEvent(object : ValueEventListener {

            override fun onCancelled(p0: DatabaseError) {
            }

            override fun onDataChange(p0: DataSnapshot) {

                if (!p0.exists()) {
                    adapter.add(Vaci())
                    rvMisRecetasMedico.adapter = adapter

                } else {

                    p0.children.forEach {
                        val receta = it.getValue(Receta::class.java)
                        adapter.add(ItemReceta(receta))
                    }

                    rvMisRecetasMedico.adapter = adapter
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
        viewHolder.itemView.dniPacientebox.text = "DNI paciente: " + receta!!.dniPaciente
        viewHolder.itemView.obrasocialbox.text ="Obra social: " + receta.obrasocial
        viewHolder.itemView.diagnosticobox.text = "Diagnostico paciente: " +receta.diagnostico

        viewHolder.itemView.farmacobox.text = "Farmaco dado: " +receta!!.farmaco
        viewHolder.itemView.cantidadbox.text = "Cantidad: " +receta.cantidadFarmaco

        viewHolder.itemView.consumobox.text = "Modo de consumo: " +receta!!.modoConsumo
        viewHolder.itemView.lugarbox.text = "Se receto para: " +receta.lugar
        viewHolder.itemView.fechabox.text = "Fecha: " +receta!!.fecha



    }

}

class Vaci() : Item<ViewHolder>() {
    override fun bind(viewHolder: ViewHolder, position: Int) {
        viewHolder.itemView.dniPacientebox.text = "No tenes recetas"
        viewHolder.itemView.diagnosticobox.text = ""

        viewHolder.itemView.farmacobox.text = ""
        viewHolder.itemView.cantidadbox.text =""

        viewHolder.itemView.consumobox.text = ""
        viewHolder.itemView.lugarbox.text =""
        viewHolder.itemView.fechabox.text = ""


    }

    override fun getLayout(): Int {
        return R.layout.diagnostico_row
    }

}

