package com.example.timer.fragments

import android.content.DialogInterface
import android.os.Bundle
import android.os.CountDownTimer
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
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
    lateinit var deleteButton: ImageButton
    lateinit var editButton: ImageButton
    lateinit var stateTextView: TextView

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
        deleteButton = fragmentView.findViewById(R.id.imgBtn_delete_details)
        editButton = fragmentView.findViewById(R.id.imgBtn_edit_details)
        backButton = fragmentView.findViewById(R.id.backBtn_detailsFragment)
        stateTextView = fragmentView.findViewById(R.id.tv_taskState_details)

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
        titleTextView.text = "$task"
        descriptionTextView.text = "$description"
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

        deleteButton.setOnClickListener {
            deleteConfirmationDialog()
        }

        doneButton.setOnClickListener {
            tasksViewModel.updateState(id, "done")
            doneUI()
            if (timerRun) {
                stopTimer()
            }
        }

        editButton.setOnClickListener {
            editTask()
        }

        if (state == "done") {
            doneUI()
        }

        if (state == "started") {
            startedUI()
        }
        if (state == "notStarted") {
            notStartedUI()
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
        if (state == "notStarted") {
            tasksViewModel.updateState(id, "started")
            doneButton.visibility = View.VISIBLE
        }
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

    private fun doneUI() {
        playImageButton.visibility = View.INVISIBLE
        doneButton.visibility = View.INVISIBLE
        timerLayout.background = resources.getDrawable(R.drawable.bg_circle_teal)
        detailsFrameLayout.background = resources.getDrawable(R.drawable.bg_blue_teal_gradient)
        stateTextView.text = "Completed"
        stateTextView.setTextColor(resources.getColor(R.color.teal_500))
        editButton.visibility = View.GONE

    }

    private fun startedUI() {
        doneButton.visibility = View.VISIBLE
        timerLayout.background = resources.getDrawable(R.drawable.bg_circle_purple)
        detailsFrameLayout.background = resources.getDrawable(R.drawable.bg_blue_purple_gradient)
        stateTextView.text = "In Progress"
        stateTextView.setTextColor(resources.getColor(R.color.purple_500))
    }

    private fun notStartedUI() {
        doneButton.visibility = View.INVISIBLE
        timerLayout.background = resources.getDrawable(R.drawable.bg_circle_red)
        detailsFrameLayout.background = resources.getDrawable(R.drawable.bg_blue_red_gradient)
        stateTextView.text = "To Start"
        stateTextView.setTextColor(resources.getColor(R.color.red_500))


    }

    private fun deleteConfirmationDialog() {
        val dialogBuilder = activity?.let { androidx.appcompat.app.AlertDialog.Builder(it) }

        dialogBuilder?.setMessage("Are you sure you want to delete this note?")
            ?.setPositiveButton("Delete", DialogInterface.OnClickListener { _, _ ->
                tasksViewModel.deleteTask(id)
                Navigation.findNavController(fragmentView)
                    .navigate(R.id.action_detailsFragment_to_listFragment)
            })?.setNegativeButton("Cancel", DialogInterface.OnClickListener { dialog, _ -> dialog.cancel()
            })
        val alert = dialogBuilder?.create()
        alert?.setTitle("Confirmation")
        alert?.show()
    }

    private fun editTask(){
        val bundle = Bundle()
        bundle!!.putString("task.id", id)
        bundle!!.putString("task.task", task)
        bundle!!.putString("task.description", description)
        bundle!!.putString("task.expectedTime", expectedTime)
        bundle!!.putString("task.spentTime", spentTime)
        bundle!!.putString("task.state", state)

        Navigation.findNavController(fragmentView)
            .navigate(R.id.action_detailsFragment_to_editTaskFragment2, bundle)
    }
}