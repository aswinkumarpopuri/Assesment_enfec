package com.example.assesment.ui

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.assesment.R
import com.example.assesment.api.model.UserPost

class mAdapter(val context: Context, val users: List<UserPost> ): RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return (ViewHolder(LayoutInflater.from(context).inflate(R.layout.users, parent, false)
        ))
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as ViewHolder).bind(users[position])
    }

    override fun getItemCount(): Int {
        return users.size
    }

    class ViewHolder (val item: View): RecyclerView.ViewHolder(item){
        fun bind(user: UserPost) {
            item.findViewById<TextView>(R.id.body).text = user.body.toString()
            item.findViewById<TextView>(R.id.title).text = user.title.toString()
            item.findViewById<TextView>(R.id.company).text = user.company.toString()

        }

    }
}