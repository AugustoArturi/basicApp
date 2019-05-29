package com.fiuba.digitalmd

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_landing.*

class LandingActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_landing)

        btnDermatologia.setOnClickListener {
            val intent = Intent(this, DermatologiaActivity::class.java)
            startActivity(intent)
        }

        btnRecetas.setOnClickListener {
            val intent = Intent(this, RecetasActivity::class.java)
            startActivity(intent)
        }
    }



}
