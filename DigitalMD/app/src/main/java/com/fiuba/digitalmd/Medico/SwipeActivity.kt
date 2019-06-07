package com.fiuba.digitalmd.Medico


import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.fiuba.digitalmd.Models.DepthPageTransformer
import com.fiuba.digitalmd.Models.Paciente
import com.fiuba.digitalmd.R
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_swipe.*


class SwipeActivity : AppCompatActivity() {

    lateinit var adapter: SwipeAdapter
    lateinit var users : DatabaseReference
    private  var userList = ArrayList<Paciente>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_swipe)
        fetchDataFromFirebase()
        viewpager_images.setPageTransformer(true, DepthPageTransformer())

    }

    private fun fetchDataFromFirebase() {

            var contador : Int = 0
            users = FirebaseDatabase.getInstance().getReference("/diagnosticos")

            users.addListenerForSingleValueEvent(object : ValueEventListener {
                var listPacientes:MutableList<Paciente> = ArrayList()
                override fun onCancelled(p0: DatabaseError) {
                }

                override fun onDataChange(p0: DataSnapshot) {
                      p0.children.forEach {
                          val user = it.getValue(Paciente::class.java)
                          listPacientes.add(user!!)


                   }
                    loadAdapter(listPacientes)
                }

            })

    }

    private fun loadAdapter(listPacientes: MutableList<Paciente>) {
        adapter = SwipeAdapter(this, listPacientes)
        viewpager_images.adapter = adapter

    }
}


