package com.fiuba.digitalmd

import android.content.Intent
import android.os.Bundle

import androidx.appcompat.app.AppCompatActivity

import kotlinx.android.synthetic.main.activity_dermatologia.*

class DermatologiaActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dermatologia)
        setSupportActionBar(findViewById(R.id.toolbarProfile))
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        btnMedico.setOnClickListener {
            val intent = Intent(this, MedicoActivity::class.java)
            startActivity(intent)
        }

        btnPaciente.setOnClickListener {
            val intent = Intent(this, PacienteActivity::class.java)
            startActivity(intent)
        }

    }

}
