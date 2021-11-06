package com.example.timer.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.timer.R
import com.example.timer.data.Task
import kotlinx.android.synthetic.main.item_row.view.*
import kotlinx.android.synthetic.main.item_row.view.img_colorBox
import kotlinx.android.synthetic.main.item_row.view.ll_item_row_colorBox
import kotlinx.android.synthetic.main.item_row.view.tv_taskDescription
import kotlinx.android.synthetic.main.item_row.view.tv_taskTitle
import kotlinx.android.synthetic.main.track_row_item.view.*

class TrackListAdapter: RecyclerView.Adapter<TrackListAdapter.ItemViewHolder>() {
    class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    private var tasks = mutableListOf<Task>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        return ItemViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.track_row_item,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val data = tasks[position]
        val time = getTime(data.spentTime.toLong())
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
                    ll_item_row_colorBox.background =
                        resources.getDrawable(R.drawable.bg_round_red)
                    img_colorBox.setImageResource(R.drawable.ic_round_play_arrow_24)
                }
                else -> {
                    ll_item_row_colorBox.background =
                        resources.getDrawable(R.drawable.bg_round_teal)
                }
            }

            tv_taskTime.text = "$time"
            tv_taskTitle.text = "${data.task}"
            tv_taskDescription.text = "${data.description}"

        }
    }

    override fun getItemCount(): Int = tasks.size

    fun replaceItems(tasks: MutableList<Task>) {
        this.tasks = tasks
        notifyDataSetChanged()
    }

    fun getTime(time: Long) : String{
        var min = time / 60000
        val sec = time % 60000 / 1000

        return if (sec < 10) {
            "$min :0$sec"
        } else {
            "$min :$sec"
        }

    }
}