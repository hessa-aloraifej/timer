package com.example.timer.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.timer.TasksModelView
import com.example.timer.R
import com.example.timer.adapters.TasksAdapter


class ListFragment : Fragment() {

    private  val tasksViewModel by lazy { ViewModelProvider(this).get(TasksModelView::class.java) }

    lateinit var recyclerView: RecyclerView
    lateinit var addTaskButton: ImageButton

    override fun onCreateView( inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_list, container, false)


        recyclerView = view.findViewById(R.id.rv_tasks)
        addTaskButton = view.findViewById(R.id.btn_addTask)

        tasksViewModel.getTask()
        tasksViewModel.getNotes().observe(viewLifecycleOwner, {
                list ->  recyclerView.adapter = TasksAdapter(list)
            recyclerView.layoutManager = LinearLayoutManager(activity)
        })

        addTaskButton.setOnClickListener {
            Navigation.findNavController(view).navigate(R.id.action_listFragment_to_addFragment)
        }
        return view
    }
}