package com.example.timer.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.navigation.Navigator
import com.example.timer.R
import com.example.timer.viewmodel.TasksModelView

class AddTaskFragment : Fragment() {
    private val tasksViewModel by lazy { ViewModelProvider(this).get(TasksModelView::class.java) }
    lateinit var fragmentView: View

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        fragmentView = inflater.inflate(R.layout.fragment_add_task, container, false)

        val taskNameAlert = fragmentView.findViewById<EditText>(R.id.edt_taskNameAlert)
        val taskDescriptionAlert =
            fragmentView.findViewById<EditText>(R.id.edt_taskDescriptionAlert)
        val seekBarTimer = fragmentView.findViewById<SeekBar>(R.id.seekBarTimer)
        val tvExpectedTimeTask = fragmentView.findViewById<TextView>(R.id.tvExpectedTimeTask)
        val addBtnAlert = fragmentView.findViewById<Button>(R.id.btn_addAlert)
        val backButton = fragmentView.findViewById<ImageButton>(R.id.backBtn_addFragment)

        backButton.setOnClickListener{
            Navigation.findNavController(fragmentView)
                .navigate(R.id.action_addTaskFragment_to_listFragment)
        }


        seekBarTimer.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                tvExpectedTimeTask.text = progress.toString()
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {

            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {

            }

        })

        addBtnAlert.setOnClickListener {
            var expectedNum = 10L
            var taskName = taskNameAlert.text.toString()
            var taskDescription = taskDescriptionAlert.text.toString()
            var taskExpectedTime = tvExpectedTimeTask.text.toString()
            if (taskExpectedTime.isNotEmpty()) {
                expectedNum = taskExpectedTime.toLong() * 1000 * 60
            }


            if (taskName.isNotEmpty() && taskDescription.isNotEmpty() && taskExpectedTime.isNotEmpty() && taskExpectedTime.isNotEmpty()) {
                tasksViewModel.saveTask(
                    "",
                    taskName,
                    taskDescription,
                    taskExpectedTime,
                    expectedNum.toString()
                )
                Navigation.findNavController(fragmentView)
                    .navigate(R.id.action_addTaskFragment_to_listFragment)
            } else {
                Toast.makeText(activity, "All fields are required", Toast.LENGTH_SHORT)
                    .show()
            }
        }

        return fragmentView
    }

}