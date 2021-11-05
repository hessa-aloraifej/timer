package com.example.timer.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.example.timer.fragments.ListFragment
import com.example.timer.R
import com.example.timer.data.Task
import kotlinx.android.synthetic.main.item_row.view.*


class TasksAdapter(private val listFragment: ListFragment) :
    RecyclerView.Adapter<TasksAdapter.ItemViewHolder>() {
    private var tasksList: List<Task> = listOf()

    // set this class to take position of one item
    inner class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        init {
            itemView.setOnClickListener { v: View ->
                val position: Int = adapterPosition
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

            when (data.state) {
                "done" -> {
                    ll_item_row_colorBox.background =
                        resources.getDrawable(R.drawable.bg_round_teal)
                    img_colorBox.setImageResource(R.drawable.ic_round_done_24)
                }
                "started" -> {
                    ll_item_row_colorBox.background =
                        resources.getDrawable(R.drawable.bg_round_purple)
                    img_colorBox.setImageResource(R.drawable.ic_round_pause_24)
                }
                "notStarted" -> {
                    ll_item_row_colorBox.background = resources.getDrawable(R.drawable.bg_round_red)
                    img_colorBox.setImageResource(R.drawable.ic_round_play_arrow_24)
                }
                else -> {
                    ll_item_row_colorBox.background =
                        resources.getDrawable(R.drawable.bg_round_teal)
                }
            }

            tv_taskTitle.text = "${data.task}"
            tv_taskDescription.text = "${data.description}"
            ll_item_row.setOnClickListener {
                listFragment.viewDetails(data)
            }
        }
    }

    override fun getItemCount(): Int = tasksList.size

    fun setData(tasks: List<Task>) {
        this.tasksList = tasks
        notifyDataSetChanged()
    }
}
