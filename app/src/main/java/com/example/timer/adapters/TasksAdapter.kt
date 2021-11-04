package com.example.timer.adapters

import android.content.Intent
import android.os.CountDownTimer
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat.startActivity
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.example.timer.R
import com.example.timer.TasksModelView
import com.example.timer.TimerDetailsActivity
import com.example.timer.data.Task
import kotlinx.android.synthetic.main.item_row.view.*


class TasksAdapter(private var tasksList: List<Task>) :
    RecyclerView.Adapter<TasksAdapter.ItemViewHolder>() {

    // set this class to take position of one item
    inner class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        init {
            itemView.setOnClickListener{ v: View ->
                val position : Int = adapterPosition
                Toast.makeText(itemView.context, "this Task # $position selected" , Toast.LENGTH_SHORT).show()
                kotlin.io.println("this Task # $position selected")
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        return ItemViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_row,
                parent,
                false
            )
        )
    }


    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val data = tasksList[position]
        holder.itemView.apply {
            tv_taskTitle.text = "${data.task}"
            tv_taskDescription.text = "${data.description}"
            ll_item_row.setOnClickListener {
                // make change to tv timer
                tv.isVisible = true


                var intent =  Intent(holder.itemView.context, TimerDetailsActivity::class.java)
                intent.putExtra("noteTitle",data.task )
                intent.putExtra("noteDescription",data.description )
                intent.putExtra("expextedTime",data.expextedTime)
                intent.putExtra("spentTime",data.spentTime)
                intent.putExtra("id",data.id)
                holder.itemView.context.startActivity(intent)
            }
        }
    }

    override fun getItemCount(): Int = tasksList.size

    fun setData(tasks: List<Task>){
        this.tasksList = tasks
        notifyDataSetChanged()
    }
}
