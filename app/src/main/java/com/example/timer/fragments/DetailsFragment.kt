package com.example.timer.fragments

import android.graphics.Color
import android.os.Bundle
import android.os.CountDownTimer
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.example.timer.R
import com.example.timer.viewmodel.TasksModelView

class DetailsFragment : Fragment() {

    private val tasksViewModel by lazy { ViewModelProvider(this).get(TasksModelView::class.java) }
    lateinit var fragmentView: View

    lateinit var detailsFrameLayout: FrameLayout
    lateinit var titleTextView: TextView
    lateinit var descriptionTextView: TextView
    lateinit var spentTimeTextView: TextView
    lateinit var expectedTimeTextView: TextView
    lateinit var doneButton: Button
    lateinit var timerLayout: LinearLayout
    lateinit var playImageButton: ImageButton
    lateinit var backButton: ImageButton

    lateinit var countDownTimer: CountDownTimer
    var timerStartTime = 0L
    var timerRun: Boolean = false

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
        detailsFrameLayout = fragmentView.findViewById(R.id.fl_details)
        titleTextView = fragmentView.findViewById(R.id.tv_taskTitle_details)
        descriptionTextView = fragmentView.findViewById(R.id.tv_taskDesc_details)
        spentTimeTextView = fragmentView.findViewById(R.id.tv_spentTimer_details)
        expectedTimeTextView = fragmentView.findViewById(R.id.tv_expectedTime_details)
        doneButton = fragmentView.findViewById(R.id.btnDone)
        timerLayout = fragmentView.findViewById(R.id.ll_timeCircle)
        playImageButton = fragmentView.findViewById(R.id.imgBtn_playStop)
        backButton = fragmentView.findViewById(R.id.backBtn_detailsFragment)

        //get data from previous fragment
        id = requireArguments().getString("task.id").toString()
        task = requireArguments().getString("task.task").toString()
        description = requireArguments().getString("task.description").toString()
        expectedTime = requireArguments().getString("task.expectedTime").toString()
        spentTime = requireArguments().getString("task.spentTime").toString()
        state = requireArguments().getString("task.state").toString()

        backButton.setOnClickListener {
            Navigation.findNavController(fragmentView)
                .navigate(R.id.action_detailsFragment_to_listFragment)
        }
        //set values in views
        titleTextView.text = "Task: $task"
        descriptionTextView.text = "Description: $description"
        spentTimeTextView.text = spentTime
        expectedTimeTextView.text = "$expectedTime:00"

        //set timer start time
        timerStartTime = spentTime.toLong()
        updateTimer(spentTimeTextView)


        //start timer button
        timerLayout.setOnClickListener {
            if (state != "done") {
                startStop(spentTimeTextView)
            }
        }

        doneButton.setOnClickListener{
            doneButton.visibility = View.INVISIBLE
            tasksViewModel.updateState(id, "done")
            playImageButton.isVisible = false
            timerLayout.background = resources.getDrawable(R.drawable.bg_circle_teal)
            detailsFrameLayout.background = resources.getDrawable(R.drawable.bg_blue_teal_gradient)
            doneButton.isVisible = false
        }

        if (state == "done") {
            playImageButton.isVisible = false
            doneButton.isVisible = false
            timerLayout.background = resources.getDrawable(R.drawable.bg_circle_teal)
            detailsFrameLayout.background = resources.getDrawable(R.drawable.bg_blue_teal_gradient)

        }

        if (state == "started") {
            doneButton.isVisible = true
            timerLayout.background = resources.getDrawable(R.drawable.bg_circle_purple)
            detailsFrameLayout.background = resources.getDrawable(R.drawable.bg_blue_purple_gradient)
        }
        if (state == "notStarted") {
            doneButton.isVisible = false
            timerLayout.background = resources.getDrawable(R.drawable.bg_circle_red)
            detailsFrameLayout.background = resources.getDrawable(R.drawable.bg_blue_red_gradient)

        }

        return fragmentView
    }

    private fun startStop(tv: TextView) {
        if (timerRun) {
            stopTimer()
        } else {
            startTimer(tv)
        }
    }

    private fun startTimer(tv: TextView) {
        tasksViewModel.updateState(id, "started")
        timerLayout.background = resources.getDrawable(R.drawable.bg_circle_teal)
        playImageButton.setImageResource(R.drawable.ic_round_pause_24)
        countDownTimer = object : CountDownTimer(timerStartTime, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                timerStartTime = millisUntilFinished
                updateTimer(tv)
            }

            override fun onFinish() {
                timerRun = false

            }
        }.start()
        timerRun = true
    }

    private fun stopTimer() {
        timerLayout.background = resources.getDrawable(R.drawable.bg_circle_purple)
        playImageButton.setImageResource(R.drawable.ic_round_play_arrow_24)
        tasksViewModel.updateTimer(id, timerStartTime.toString())
        countDownTimer?.cancel()
        timerRun = false
        doneButton.isVisible = true
        doneButton.setOnClickListener {
            doneButton.setBackgroundColor(Color.GREEN)
            state = "done"
            tasksViewModel.updateState(id, state)
        }
    }

    fun updateTimer(tv: TextView) {
        var min = timerStartTime / 60000
        val sec = timerStartTime % 60000 / 1000

        if (sec < 10) {
            tv.text = "$min :0$sec"
        } else {
            tv.text = "$min :$sec"
        }

    }

    override fun onStop() {
        super.onStop()
        tasksViewModel.updateTimer(id, timerStartTime.toString())
    }

    override fun onDestroyView() {
        super.onDestroyView()
        tasksViewModel.updateTimer(id, timerStartTime.toString())
    }

    override fun onPause() {
        super.onPause()
        tasksViewModel.updateTimer(id, timerStartTime.toString())
    }
}