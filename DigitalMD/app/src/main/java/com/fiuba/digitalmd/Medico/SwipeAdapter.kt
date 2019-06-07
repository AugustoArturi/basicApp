package com.fiuba.digitalmd.Medico

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.view.LayoutInflater
import android.view.View

import android.view.ViewGroup
import android.widget.*

import androidx.viewpager.widget.PagerAdapter
import androidx.viewpager.widget.ViewPager
import com.fiuba.digitalmd.Models.InfoActual
import com.fiuba.digitalmd.Models.Paciente
import com.fiuba.digitalmd.R
import com.google.firebase.database.FirebaseDatabase
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.row_coment.view.*


class SwipeAdapter (internal var context: Context, internal var usersList:List<Paciente>): PagerAdapter() {


    internal val layoutInflater : LayoutInflater = LayoutInflater.from(context)



    override fun isViewFromObject(p0: View, p1: Any): Boolean {
        return p0==p1
    }

    override fun getCount(): Int {
        return usersList.size
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        (container as ViewPager).removeView(`object` as View)
    }


    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val view = layoutInflater.inflate(R.layout.content_item,container,false)
        val dermaImage = view.findViewById<View>(R.id.iv_photo) as ImageView
        val epigrafe = view.findViewById<View>(R.id.tv_name) as TextView
        val btnOk = view.findViewById<View>(R.id.btnOk) as ImageButton
        val btnConsultarConOtro = view.findViewById<View>(R.id.btnConsultarConOtro) as ImageButton
        val btnMal = view.findViewById<View>(R.id.btnIrAlMedico) as ImageButton
        val btnComentario = view.findViewById<View>(R.id.btnComentario) as ImageButton
        Picasso.get().load(usersList[position].urlImage).into(dermaImage)
        epigrafe.text = usersList[position].nombre + ": " + usersList[position].descripcion
        val uid = usersList[position].uid
        btnOk.setOnClickListener {
            //Toast.makeText(context,"OK clicked",Toast.LENGTH_SHORT).show()
            val actualizarData = FirebaseDatabase.getInstance().getReference("diagnosticos/$uid")
            actualizarData.child("/estadoDiagnostico").setValue("Ok")
        }

        btnConsultarConOtro.setOnClickListener {
            var builder = AlertDialog.Builder(context)
            builder.setTitle("Estimado doctor/a")
            builder.setMessage(" ¿ Deseas que opine otro profesional ?")
            builder.setPositiveButton("Si",{dialog: DialogInterface?, which: Int ->  } )
            builder.show()
        }

        btnMal.setOnClickListener {
            val actualizarData = FirebaseDatabase.getInstance().getReference("diagnosticos/$uid")
            actualizarData.child("/estadoDiagnostico").setValue("Ir al medico")
        }

        btnComentario.setOnClickListener {
            val mDialogView = LayoutInflater.from(context).inflate(R.layout.row_coment, null)
            var builder = AlertDialog.Builder(context)
            builder.setView(mDialogView)
            builder.setTitle("Estimado doctor/a")
            builder.setMessage(" Escriba un comentario para el paciente")
            val mAlertDialago = builder.show()

            mDialogView.btnEnviarComentario.setOnClickListener {
                val actualizarData = FirebaseDatabase.getInstance().getReference("diagnosticos/$uid")
                actualizarData.child("/comentarioMedico").setValue(mDialogView.etComentarioDelDoc.text.toString())
                mAlertDialago.dismiss()
            }
        }




        container.addView(view)

        return view
    }




}