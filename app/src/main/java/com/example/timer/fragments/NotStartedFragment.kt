package com.example.timer.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.timer.R
import com.example.timer.activities.TrackActivity
import com.example.timer.adapters.TrackListAdapter
import com.example.timer.viewmodel.TasksModelView
import kotlinx.android.synthetic.main.fragment_not_started.view.*

class NotStartedFragment : Fragment() {
    private val tasksViewModel by lazy { ViewModelProvider(this).get(TasksModelView::class.java) }

    private lateinit var adapter: TrackListAdapter
    lateinit var progressBar: ProgressBar

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_not_started, container, false)

        adapter = TrackListAdapter()

        progressBar = view.findViewById(R.id.progressBar)
        val numOfTasksTextView = view.findViewById<TextView>(R.id.tv_NumOfTasks)
        val allTasksTextView = view.findViewById<TextView>(R.id.tv_AllTasks)

        if (!TrackActivity.getData) {
            TrackActivity.getData = true

            tasksViewModel.getAllTasks()
            tasksViewModel.getTasksList.observe(viewLifecycleOwner, { list ->
                for (task in list) {
                    when (task.state) {
                        "notStarted" -> TrackActivity.notStartedListObj.add(task)
                        "done" -> TrackActivity.completedListObj.add(task)
                        else -> TrackActivity.startedListObj.add(task)
                    }
                }
                progressBar.visibility = View.GONE
                adapter.replaceItems(TrackActivity.notStartedListObj)
                numOfTasksTextView.text = TrackActivity.notStartedListObj.size.toString()
                allTasksTextView.text = "/${TrackActivity.startedListObj.size + TrackActivity.notStartedListObj.size + TrackActivity.completedListObj.size}"

            })

            view.rv_notStarted_tasks.adapter = adapter
            view.rv_notStarted_tasks.layoutManager = LinearLayoutManager(context)
            view.rv_notStarted_tasks.hasFixedSize()


        } else {
            progressBar.visibility = View.GONE
            adapter.replaceItems(TrackActivity.notStartedListObj)
            view.rv_notStarted_tasks.adapter = adapter
            view.rv_notStarted_tasks.layoutManager = LinearLayoutManager(context)
            view.rv_notStarted_tasks.hasFixedSize()
            numOfTasksTextView.text = TrackActivity.notStartedListObj.size.toString()
            allTasksTextView.text = "/${TrackActivity.startedListObj.size + TrackActivity.notStartedListObj.size + TrackActivity.completedListObj.size}"
        }

        return view
    }

}