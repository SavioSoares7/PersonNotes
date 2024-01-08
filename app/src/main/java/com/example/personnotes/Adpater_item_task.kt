package com.example.personnotes

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class Adpater_item_task(
    private val myTask: List<String>
) : RecyclerView.Adapter<Adpater_item_task.MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_adapter, parent, false)
        return MyViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return myTask.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val task = myTask[position]
        holder.textName.text = task
    }

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textName: TextView = itemView.findViewById(R.id.text_task)
    }
}
