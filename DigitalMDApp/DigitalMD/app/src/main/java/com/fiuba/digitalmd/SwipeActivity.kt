package com.fiuba.digitalmd


import android.os.Bundle
import android.util.Log
import android.widget.Adapter
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.*
import com.squareup.picasso.Picasso
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.Item
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.activity_swipe.*
import kotlinx.android.synthetic.main.content_item.view.*
import link.fls.swipestack.SwipeStack


class SwipeActivity : AppCompatActivity() {

    lateinit var adapter: SwipeAdapter
    lateinit var users : DatabaseReference
    private  var userList = ArrayList<User>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_swipe)
        fetchDataFromFirebase()
        viewpager_images.setPageTransformer(true,DepthPageTransformer())

    }

    private fun fetchDataFromFirebase() {

            var contador : Int = 0
            users = FirebaseDatabase.getInstance().getReference("/users")

            users.addListenerForSingleValueEvent(object : ValueEventListener {
                var listUsers:MutableList<User> = ArrayList()
                override fun onCancelled(p0: DatabaseError) {
                }

                override fun onDataChange(p0: DataSnapshot) {

                   for (userSnapshot in p0.children) {
                       val user = userSnapshot.getValue(User::class.java)
                       listUsers.add(user!!)


                   }
                    loadAdapter(listUsers)
                }

            })

    }

    private fun loadAdapter(listUsers: MutableList<User>) {
        adapter = SwipeAdapter(this, listUsers)
        viewpager_images.adapter = adapter

    }
}

class UserItem(val currenUser: User) : Item<ViewHolder>() {
    override fun bind(viewHolder: ViewHolder, position: Int) {
        viewHolder.itemView.tv_name.text = currenUser.name
        Picasso.get().load(currenUser.urlImage).into(viewHolder.itemView.iv_photo)
    }

    override fun getLayout(): Int {
        return R.layout.content_item
    }
}
