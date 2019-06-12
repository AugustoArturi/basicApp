package com.fiuba.digitalmd.Paciente

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import com.fiuba.digitalmd.Models.InfoActual
import com.fiuba.digitalmd.Models.Paciente
import com.fiuba.digitalmd.Models.User
import com.fiuba.digitalmd.R
import com.fiuba.digitalmd.SignedInActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.squareup.picasso.Picasso
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.Item
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.activity_mis_diagnosticos.*
import kotlinx.android.synthetic.main.diagnostico_row.view.*

class MisDiagnosticosActivity : SignedInActivity() {



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mis_diagnosticos)


        val toolbar: Toolbar = findViewById(R.id.toolbarProfile)
        setSupportActionBar(toolbar)
        leerUsuarioDeFirebase()

        leerDiagnosticosDeFirebase(toolbar)

        btnNuevaConsulta.setOnClickListener {
            startActivity(Intent(this, PacienteActivity::class.java))
        }
    }

    private fun leerDiagnosticosDeFirebase(toolbar:Toolbar) {
        val adapter = GroupAdapter<ViewHolder>()
        val uid = FirebaseAuth.getInstance().uid
        val ref = FirebaseDatabase.getInstance().getReference("/diagnosticos/$uid")
        ref.addListenerForSingleValueEvent(object : ValueEventListener {

            override fun onCancelled(p0: DatabaseError) {
            }

            override fun onDataChange(p0: DataSnapshot) {

                if(!p0.exists()){
                    adapter.add(Vac())
                    rvMisDiagnosticos.adapter = adapter
                    toolbar.title = InfoActual.getUsuarioActual().nombre + " " +InfoActual.getUsuarioActual().apellido
                }
                else {

                    val paciente = p0.getValue(Paciente::class.java)
                    toolbar.title = paciente!!.nombre + " " + paciente!!.apellido
                    adapter.add(PacienteItem(paciente!!))
                }

                    rvMisDiagnosticos.adapter = adapter
               }


        })

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
                }
            })

        }

}

class PacienteItem(val paciente: Paciente) : Item<ViewHolder>() {
    override fun bind(viewHolder: ViewHolder, position: Int) {
        viewHolder.itemView.txtResultado.text = paciente.estadoDiagnostico
        viewHolder.itemView.txtDesc.text = paciente.descripcion

        Picasso.get().load(paciente.urlImage).into(viewHolder.itemView.iv_photo_diagnostico)
    }

    override fun getLayout(): Int {
        return R.layout.diagnostico_row
    }

}


class Vac() : Item<ViewHolder>() {
    override fun bind(viewHolder: ViewHolder, position: Int) {
        viewHolder.itemView.txtResultado.text = "No tenes diagnosticos"
        viewHolder.itemView.txtDesc.text = ""


    }

    override fun getLayout(): Int {
        return R.layout.diagnostico_row
    }

}

