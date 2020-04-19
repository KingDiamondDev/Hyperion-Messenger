package com.devc.hyperionmessenger

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.squareup.picasso.Picasso
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.ViewHolder
import com.xwray.groupie.Item
import kotlinx.android.synthetic.main.activity_newm.*
import kotlinx.android.synthetic.main.newm_user.view.*

class NewMessages : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_newm)

        supportActionBar?.title = "Select User"

// work on implementing groupie and figuring out how to get buttons to show up when launching newm_user layout

//        val adapter = GroupAdapter<ViewHolder>()
//        adapter.add(UserItem())
//        adapter.add(UserItem())
//        adapter.add(UserItem())
//
//        recycle_newm.adapter = adapter

        fetchUsers()

    }

    private fun fetchUsers() {
        val ref = FirebaseDatabase.getInstance().getReference("/users")
        ref.addListenerForSingleValueEvent(object: ValueEventListener {

            override fun onDataChange(p0: DataSnapshot) {
                val adapter = GroupAdapter<ViewHolder>()

                p0.children.forEach {
                    Log.d("NewMessage", it.toString())
                    val user = it.getValue(User::class.java)
                    if (user != null) {
                        adapter.add(UserItem(user))
                    }
                }

                recycle_newm.adapter = adapter
            }

            override fun onCancelled(p0: DatabaseError) {

            }
        })
    }
}

class UserItem(val user: User): Item<ViewHolder>() {
    override fun bind(viewHolder: ViewHolder, position: Int) {
        viewHolder.itemView.un.text = user.username

        Picasso.get().load(user.profileImageUrl).into(viewHolder.itemView.un_profilepic)
    }

    override fun getLayout(): Int {
        return R.layout.newm_user
    }
}