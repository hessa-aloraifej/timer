package com.example.timer

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.widget.Button
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider

class TimerDetailsActivity : AppCompatActivity() {

    private  val tasksViewModel by lazy { ViewModelProvider(this).get(TasksModelView::class.java) }

    lateinit var tvTitle : TextView
    lateinit var tvDescription : TextView
    lateinit var tvTimer : TextView
    lateinit var tvExpectedTime : TextView
    lateinit var btnDone : Button
    var id = ""
    var start = 0L
    var timeRun: Boolean = false
    lateinit var countDownTimer: CountDownTimer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_timer_details)
        //initi
        tvTitle = findViewById(R.id.tvTitle)
        tvDescription = findViewById(R.id.tvDescription)
        tvTimer = findViewById(R.id.tvTimer)
        tvExpectedTime = findViewById(R.id.tvExpectedTime)
        btnDone = findViewById(R.id.btnDone)
        //get data from intent
        tvTitle.text ="Task : "+intent.getStringExtra("noteTitle")
        tvDescription.text = "Description : "+intent.getStringExtra("noteDescription")

        tvTimer.text = intent.getStringExtra("spentTime")
        tvExpectedTime.text = intent.getStringExtra("expextedTime")+":00"

        start = intent.getStringExtra("spentTime")!!.toLong()
        Log.i("Start value :","$start")
        id = intent.getStringExtra("id")!!


        btnDone.isVisible = false
        startStop(tvTimer)
        tvTimer.setOnClickListener{
            startStop(tvTimer)
        }

    }

    override fun onStop() {
        super.onStop()
        tasksViewModel.updateTimer(id , start.toString())
    }

    override fun onDestroy() {
        super.onDestroy()
        tasksViewModel.updateTimer(id , start.toString())
    }

    fun startStop(tv: TextView) {
        if(timeRun) {
            stopTimer()
        }else{
            startTimer(tv)
        }
    }

    private fun startTimer(tv: TextView) {
        countDownTimer = object : CountDownTimer(start, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                start = millisUntilFinished
                updatTimer(tv)
            }
            override fun onFinish(){
                timeRun = false
                btnDone.isVisible = true
                btnDone.setOnClickListener{btnDone.setBackgroundColor(Color.GREEN)}
            }
        }.start()
        timeRun = true
    }

    fun stopTimer() {
        countDownTimer?.cancel()
        timeRun = false
        btnDone.isVisible = true
        btnDone.setOnClickListener{btnDone.setBackgroundColor(Color.GREEN)}
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