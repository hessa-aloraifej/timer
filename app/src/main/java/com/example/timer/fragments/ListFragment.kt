package com.example.timer.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.timer.R
import com.example.timer.viewmodel.TasksModelView
import com.example.timer.adapters.TasksAdapter
import com.example.timer.data.Task

class ListFragment : Fragment() {
    private val tasksViewModel by lazy { ViewModelProvider(this).get(TasksModelView::class.java) }
    lateinit var fragmentView: View

    lateinit var recyclerView: RecyclerView
    lateinit var tasksAdapter: TasksAdapter
    lateinit var addTaskButton: ImageButton
    lateinit var backButton: ImageButton


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        fragmentView = inflater.inflate(R.layout.fragment_list, container, false)

        recyclerView = fragmentView.findViewById(R.id.rv_tasks)
        tasksAdapter = TasksAdapter(this)
        recyclerView.adapter = tasksAdapter
        recyclerView.layoutManager = LinearLayoutManager(activity)

        addTaskButton = fragmentView.findViewById(R.id.btn_addTask)
        backButton = fragmentView.findViewById(R.id.backBtn_listFragment)
        tasksViewModel.getAllTasks()
        tasksViewModel.getTasksList.observe(viewLifecycleOwner, { list ->
            tasksAdapter.setData(list)
        })

        backButton.setOnClickListener{
            activity?.onBackPressed()
        }
        addTaskButton.setOnClickListener {
            addTask()
        }

        return fragmentView
    }

    fun viewDetails(task: Task) {

        val bundle = Bundle()
        bundle!!.putString("task.id", task.id)
        bundle!!.putString("task.task", task.task)
        bundle!!.putString("task.description", task.description)
        bundle!!.putString("task.expectedTime", task.expectedTime)
        bundle!!.putString("task.spentTime", task.spentTime)
        bundle!!.putString("task.state", task.state)

        Navigation.findNavController(fragmentView)
            .navigate(R.id.action_listFragment_to_detailsFragment, bundle)
    }

    fun addTask() {

        Navigation.findNavController(fragmentView)
            .navigate(R.id.action_listFragment_to_addTaskFragment)



    }
}