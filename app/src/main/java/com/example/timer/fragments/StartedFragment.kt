package com.example.timer.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.timer.R
import com.example.timer.activities.TrackActivity
import com.example.timer.adapters.TrackListAdapter
import kotlinx.android.synthetic.main.fragment_started.view.*

class StartedFragment: Fragment() {

    private lateinit var adapter: TrackListAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_started, container, false)

        adapter = TrackListAdapter()

        adapter.replaceItems(TrackActivity.startedListObj)
        view.rv_started_tasks.adapter = adapter
        view.rv_started_tasks.layoutManager = LinearLayoutManager(context)
        view.rv_started_tasks.hasFixedSize()

        val numOfTasksTextView = view.findViewById<TextView>(R.id.tv_NumOfTasks)
        val allTasksTextView = view.findViewById<TextView>(R.id.tv_AllTasks)

        numOfTasksTextView.text = TrackActivity.startedListObj.size.toString()
        allTasksTextView.text = "/${TrackActivity.startedListObj.size + TrackActivity.notStartedListObj.size + TrackActivity.completedListObj.size}"

        return view
    }

}