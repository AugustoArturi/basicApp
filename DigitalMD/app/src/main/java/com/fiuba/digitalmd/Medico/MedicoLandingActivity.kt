package com.fiuba.digitalmd.Medico

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import com.fiuba.digitalmd.Models.InfoActual
import com.fiuba.digitalmd.Models.Medico
import com.fiuba.digitalmd.R
import com.fiuba.digitalmd.SignedInActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_medico_landing.*

class MedicoLandingActivity : SignedInActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_medico_landing)

        val toolbar: Toolbar = findViewById(R.id.toolbarProfile)
        setSupportActionBar(toolbar)
        leerUsuarioDeFirebase(toolbar)

        btnHacerDiagnosticos.setOnClickListener {
            startActivity(Intent(baseContext, SwipeActivity::class.java))
        }

        btnNuevaReceta.setOnClickListener {
            startActivity(Intent(baseContext, HacerRecetaActivity::class.java))
        }

        btnVerRecetas.setOnClickListener {
            startActivity(Intent(baseContext, VerRecetasMedicoActivity::class.java))
        }
    }

    private fun setViews(toolbar: Toolbar) {

        toolbar.title = InfoActual.getMedicoActual().apellido + " " + InfoActual.getMedicoActual().matricula
        //Seteo logo de la orga
        Picasso.get().load(InfoActual.getMedicoActual().url).into(imgMedicoProfile)

    }

    private fun leerUsuarioDeFirebase(toolbar: Toolbar) {
        val uid = FirebaseAuth.getInstance().uid
        val ref = FirebaseDatabase.getInstance().getReference("/signup/$uid")

        ref.addListenerForSingleValueEvent(object : ValueEventListener {

            override fun onCancelled(p0: DatabaseError) {
                Toast.makeText(baseContext, "Medico no leido", Toast.LENGTH_SHORT).show()
            }

            override fun onDataChange(p0: DataSnapshot) {

                val medico = p0.getValue(Medico::class.java)
                InfoActual.setMedicoActual(medico!!)
                Toast.makeText(baseContext, "Medico leido", Toast.LENGTH_SHORT).show()
                setViews(toolbar)
            }
        })

    }
}
