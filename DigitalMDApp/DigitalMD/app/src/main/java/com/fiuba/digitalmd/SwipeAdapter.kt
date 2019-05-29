package com.fiuba.digitalmd

import android.app.Activity

import com.fiuba.digitalmd.R
import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View

import android.view.ViewGroup
import android.view.ViewParent
import android.widget.*

import androidx.viewpager.widget.PagerAdapter
import androidx.viewpager.widget.ViewPager
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.squareup.picasso.Picasso


class SwipeAdapter (internal var context: Context, internal var usersList:List<User>): PagerAdapter() {


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

        Picasso.get().load(usersList[position].urlImage).into(dermaImage)
        epigrafe.text = usersList[position].name
        btnOk.setOnClickListener {
            Toast.makeText(context,"OK clicked",Toast.LENGTH_SHORT).show()
        }
        container.addView(view)

        return view
    }




}