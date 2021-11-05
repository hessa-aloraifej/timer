package com.example.timer.fragments

import android.graphics.Color
import android.os.Bundle
import android.os.CountDownTimer
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import com.example.timer.R
import com.example.timer.viewmodel.TasksModelView

class DetailsFragment : Fragment() {

    private  val tasksViewModel by lazy { ViewModelProvider(this).get(TasksModelView::class.java) }
    lateinit var fragmentView: View

    lateinit var tvTitle : TextView
    lateinit var tvDescription : TextView
    lateinit var tvTimer : TextView
    lateinit var tvExpectedTime : TextView
    lateinit var btnDone : Button

    lateinit var countDownTimer: CountDownTimer
    var start = 0L
    var timeRun: Boolean = false

    var id = ""
    var task = ""
    var description = ""
    var expectedTime = ""
    var spentTime = ""
    var state = ""


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        fragmentView = inflater.inflate(R.layout.fragment_details, container, false)

        //init views
        tvTitle = fragmentView.findViewById(R.id.tvTitle)
        tvDescription = fragmentView.findViewById(R.id.tvDescription)
        tvTimer = fragmentView.findViewById(R.id.tvTimer)
        tvExpectedTime = fragmentView.findViewById(R.id.tvExpectedTime)
        btnDone = fragmentView.findViewById(R.id.btnDone)

        //get data from previous fragment
        id = requireArguments().getString("task.id").toString()
        task = requireArguments().getString("task.task").toString()
        description = requireArguments().getString("task.description").toString()
        expectedTime = requireArguments().getString("task.expectedTime").toString()
        spentTime = requireArguments().getString("task.spentTime").toString()
        state = requireArguments().getString("task.state").toString()

        //set values in views
        tvTitle.text = "Task: $task"
        tvDescription.text = "Description: $description"
        tvTimer.text = spentTime
        tvExpectedTime.text = "$expectedTime:00"

        //set timer start time
        start = spentTime.toLong()

        //start timer once activity start
        startStop(tvTimer)

        if(state == "done"){
            tvTimer.visibility = View.INVISIBLE
        }

        btnDone.isVisible = false

        tvTimer.setOnClickListener{
            startStop(tvTimer)
        }

        return fragmentView
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
                btnDone.setOnClickListener{
                    btnDone.setBackgroundColor(Color.GREEN)
                }
            }
        }.start()
        timeRun = true
    }

    fun stopTimer() {
        countDownTimer?.cancel()
        timeRun = false
        btnDone.isVisible = true
        btnDone.setOnClickListener{btnDone.setBackgroundColor(Color.GREEN)
            state = "done"
            tasksViewModel.updateState(id,state)
        }
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

    override fun onStop() {
        super.onStop()
        tasksViewModel.updateTimer(id,start.toString())
    }

    override fun onDestroyView() {
        super.onDestroyView()
        tasksViewModel.updateTimer(id,start.toString())
    }

    override fun onPause() {
        super.onPause()
        tasksViewModel.updateTimer(id,start.toString())
    }
}