package com.devc.hyperionmessenger

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieViewHolder
import com.xwray.groupie.Item
import kotlinx.android.synthetic.main.activity_newm.*

class NewMessages : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_newm)

        supportActionBar?.title = "Who's the receiver?"

// work on implementing groupie and figuring out how to get buttons to show up when launching newm_user layout
        /*
        val adapter = GroupAdapter<GroupieViewHolder>()
        adapter.add(UserItem())
        adapter.add(UserItem())
        adapter.add(UserItem())

        //recycle_newm.adapter =
        */
    }
}


class UserItem: Item<GroupieViewHolder>() {

    override fun bind(viewHolder: GroupieViewHolder, position: Int) {
        // will be called later
    }

    override fun getLayout(): Int {
        return R.layout.newm_user
    }
}