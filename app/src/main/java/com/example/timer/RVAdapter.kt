package com.example.timer

import android.os.CountDownTimer
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_row.view.*

class RVAdapter( val list: List<Task>) : RecyclerView.Adapter<RVAdapter.ItemViewHolder>() {
    //lateinit var tv:TextView
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
        val data = list[position]

       // val text = updatTimer()


        holder.itemView.apply {


            textView.text = "${data.task}"
            textView2.text="${data.description}"
            linear.setOnClickListener {
                tv.isVisible = true
                timeRun = !timeRun
                println(timeRun)
                startStop(tv)



            }

            //    delbtn.setOnClickListener(){
            //                activity. confirmAlert(data.id)
            //
            //            }
            //            editbtn.setOnClickListener {
            //                activity.customAlert(data.id)
            //            }

        }
    }

    override fun getItemCount(): Int = list.size

   fun startStop(tv: TextView) {
       startTimer(tv)
    }

    private fun startTimer(tv: TextView) {
        countDownTimer = object : CountDownTimer(start, 1000) {

            override fun onFinish() {

            }

            //
            override fun onTick(millisUntilFinished: Long) {
                start = millisUntilFinished
                updatTimer(tv)
            }

        }.start()
        timeRun = true
    }

    fun stopTimer() {
        countDownTimer.cancel()
        timeRun = false
    }

    fun updatTimer(tv: TextView) {
        var min = start / 60000
        val sec = start % 60000 / 1000

        if (sec < 10) {
            tv.text = "$min :0$sec"
        } else {
            tv.text = "$min :$sec"
        }


    }
}