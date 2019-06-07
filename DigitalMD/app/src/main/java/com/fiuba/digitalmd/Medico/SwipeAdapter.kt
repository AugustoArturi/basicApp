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
import com.fiuba.digitalmd.Models.Paciente
import com.fiuba.digitalmd.R
import com.squareup.picasso.Picasso


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

        Picasso.get().load(usersList[position].urlImage).into(dermaImage)
        epigrafe.text = usersList[position].nombre
        btnOk.setOnClickListener {
            Toast.makeText(context,"OK clicked",Toast.LENGTH_SHORT).show()
        }

        btnConsultarConOtro.setOnClickListener {
            var builder = AlertDialog.Builder(context)
            builder.setTitle("Estimado doctor/a")
            builder.setMessage(" ¿ Deseas que opine otro profesional ?")
            builder.setPositiveButton("Si",{dialog: DialogInterface?, which: Int ->  } )
            builder.show()
        }
        container.addView(view)

        return view
    }




}