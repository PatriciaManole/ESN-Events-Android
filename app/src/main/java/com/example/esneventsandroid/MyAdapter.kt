package com.example.esneventsandroid

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class MyAdapter(private val eventList : ArrayList<events>) : RecyclerView.Adapter<MyAdapter.MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.eventitem, parent,false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        val currentitem = eventList[position]

        holder.evname.text = currentitem.name
        holder.evdescription.text = currentitem.description
        holder.evdate.text = currentitem.date

        val btnShare = holder.itemView.findViewById<ImageButton>(R.id.btnShare)

        btnShare.setOnClickListener {
            val shareIntent = Intent(Intent.ACTION_SEND)
            shareIntent.type = "text/plain"
            shareIntent.putExtra(Intent.EXTRA_TEXT, "Check out :\n${currentitem.name}\nInfo:(${currentitem.description})\nDate: ${currentitem.date} ")
            holder.itemView.context.startActivity(Intent.createChooser(shareIntent, "Share via"))
        }
    }


    override fun getItemCount(): Int {
        return eventList.size
    }

    inner class MyViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){
        val evname : TextView = itemView.findViewById(R.id.evname)
        val evdescription : TextView = itemView.findViewById(R.id.evdesc)
        val evdate : TextView = itemView.findViewById(R.id.evdate)
    }
}