package com.example.timer.adapters

import android.os.CountDownTimer
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.example.timer.R
import com.example.timer.data.Task
import kotlinx.android.synthetic.main.item_row.view.*

class TasksAdapter(private var tasksList: List<Task>) :
    RecyclerView.Adapter<TasksAdapter.ItemViewHolder>() {

    var start = 600000L
    var timeRun: Boolean = false
    lateinit var countDownTimer: CountDownTimer

    class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

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
                tv.isVisible = true
                timeRun = !timeRun
                println(timeRun)
                startStop(tv)
            }

        }
    }

    override fun getItemCount(): Int = tasksList.size

    fun setData(tasks: List<Task>){
        this.tasksList = tasks
        notifyDataSetChanged()
    }

    private fun startStop(tv: TextView) {
        startTimer(tv)
    }

    private fun startTimer(tv: TextView) {
        countDownTimer = object : CountDownTimer(start, 1000) {

            override fun onFinish() {

            }

            //
            override fun onTick(millisUntilFinished: Long) {
                start = millisUntilFinished
                updateTimer(tv)
            }

        }.start()
        timeRun = true
    }

    fun stopTimer() {
        countDownTimer.cancel()
        timeRun = false
    }

    fun updateTimer(tv: TextView) {
        var min = start / 60000
        val sec = start % 60000 / 1000

        if (sec < 10) {
            tv.text = "$min :0$sec"
        } else {
            tv.text = "$min :$sec"
        }


    }
}