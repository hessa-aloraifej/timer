package com.example.timer.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.example.timer.R
import com.example.timer.viewmodel.TasksModelView

class EditTaskFragment : Fragment() {
    private val tasksViewModel by lazy { ViewModelProvider(this).get(TasksModelView::class.java) }
    lateinit var fragmentView: View

    lateinit var timerTextView: TextView
    lateinit var taskNameAlert: EditText
    lateinit var taskDescriptionAlert: EditText
    lateinit var editButton: Button
    lateinit var backButton: ImageButton

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
        fragmentView = inflater.inflate(R.layout.fragment_edit_task, container, false)

        timerTextView = fragmentView.findViewById(R.id.tvExpectedTimeEditTask)
        taskNameAlert = fragmentView.findViewById(R.id.edt_taskNameEditAlert)
        taskDescriptionAlert =
            fragmentView.findViewById(R.id.edt_taskDescriptionEditAlert)
        editButton = fragmentView.findViewById(R.id.btn_editAlert)
        backButton = fragmentView.findViewById(R.id.backBtn_editFragment)

        //get data from previous fragment
        id = requireArguments().getString("task.id").toString()
        task = requireArguments().getString("task.task").toString()
        description = requireArguments().getString("task.description").toString()
        expectedTime = requireArguments().getString("task.expectedTime").toString()
        expectedTime = requireArguments().getString("task.expectedTime").toString()
        spentTime = requireArguments().getString("task.spentTime").toString()
        state = requireArguments().getString("task.state").toString()


        taskNameAlert.setText(task)
        taskDescriptionAlert.setText(description)
        timerTextView.setText(expectedTime)

        editButton.setOnClickListener {
            updateTask()
        }


        backButton.setOnClickListener {
            back()
        }

        return fragmentView
    }

    private fun updateTask() {
        var taskName = taskNameAlert.text.toString()
        var taskDescription = taskDescriptionAlert.text.toString()


        if (taskName.isNotEmpty() && taskDescription.isNotEmpty()) {
            task = taskName
            description = taskDescription

            tasksViewModel.updateTask(
                id,
                taskName,
                taskDescription,
            )
            back()
        } else {
            Toast.makeText(activity, "You Should Complete All Information", Toast.LENGTH_SHORT)
                .show()
        }
    }

    private fun back(){
        val bundle = Bundle()
        bundle!!.putString("task.id", id)
        bundle!!.putString("task.task", task)
        bundle!!.putString("task.description", description)
        bundle!!.putString("task.expectedTime", expectedTime)
        bundle!!.putString("task.spentTime", spentTime)
        bundle!!.putString("task.state", state)

        Navigation.findNavController(fragmentView)
            .navigate(R.id.action_editTaskFragment_to_detailsFragment2, bundle)
    }

}