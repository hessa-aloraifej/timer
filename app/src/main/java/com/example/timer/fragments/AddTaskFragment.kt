package com.example.timer.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.example.timer.TasksModelView
import com.example.timer.R


class AddTaskFragment : Fragment() {

    private  val tasksViewModel by lazy { ViewModelProvider(this).get(TasksModelView::class.java) }

    lateinit var taskNameEditText: EditText
    lateinit var taskDescriptionEditText: EditText
    lateinit var addButton: Button
    lateinit var viewTasksButton: Button

    override fun onCreateView( inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view= inflater.inflate(R.layout.fragment_add, container, false)

        taskNameEditText = view.findViewById(R.id.edt_taskName)
        taskDescriptionEditText = view.findViewById(R.id.edt_taskDescription)
        addButton = view.findViewById(R.id.btn_add)
        viewTasksButton = view.findViewById(R.id.btn_viewTasks)

        addButton.setOnClickListener {
            tasksViewModel.saveTask(taskNameEditText.text.toString(),taskDescriptionEditText.text.toString())
            clearFields()

        }

        viewTasksButton.setOnClickListener {
            Navigation.findNavController(view).navigate(R.id.action_addFragment_to_listFragment)
        }
        return view
    }

    private fun clearFields(){
        taskNameEditText.text.clear()
        taskDescriptionEditText.text.clear()
    }


}