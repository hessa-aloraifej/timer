package com.example.timer.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.timer.R
import com.example.timer.TrackActivity
import com.example.timer.TrackActivity.Companion.db
import com.example.timer.data.Task
import kotlinx.android.synthetic.main.fragment_not_started.view.*
import kotlinx.android.synthetic.main.item_row.view.*

class NotStartedFragment : Fragment() {

    private lateinit var adapter: NotStartedAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_not_started, container, false)

        adapter = NotStartedAdapter()

        if(!TrackActivity.getData){
            Log.d("llkkkkll", "9000009")
            TrackActivity.getData =true
            db.collection("Task")
                .get()
                .addOnSuccessListener { result ->
                    var startedList: ArrayList<Task> = arrayListOf()
                    var notStartedList: ArrayList<Task> = arrayListOf()
                    var completedList: ArrayList<Task> = arrayListOf()


                    var taskText = ""
                    var descriptionText = ""
                    var expectedTime = ""
                    var spentTime = ""
                    var state = ""

                    for (document in result) {
                        Log.d("plplplp", "${document.id} => ${document.data}")
                        document.data.map { (key, value)
                            ->
                            when (key) {
                                "taskText" -> taskText = value as String
                                "descriptionText" -> descriptionText = value as String
                                "expectedTime" -> expectedTime = value as String
                                "spentTime" -> spentTime = value as String
                                "state" -> state = value as String
                            }
                        }
                        when (state) {
                            "notStarted" -> notStartedList.add(Task(document.id, taskText, descriptionText, expectedTime, spentTime,state))
                            "done" -> completedList.add(Task(document.id, taskText, descriptionText, expectedTime, spentTime,state))
                            else -> startedList.add(Task(document.id, taskText, descriptionText, expectedTime, spentTime,state))
                        }
                    }
                    TrackActivity.notStartedList = notStartedList
                    TrackActivity.completedList = completedList
                    TrackActivity.startedList = startedList

                    adapter.replaceItems(notStartedList)
                    view.rv_notStarted_tasks.adapter = adapter
                    view.rv_notStarted_tasks.layoutManager = LinearLayoutManager(context)
                    view.rv_notStarted_tasks.hasFixedSize()
                    TrackActivity.notStartedList = notStartedList
                }
                .addOnFailureListener { exception ->
                    Log.w("plplplp", "Error getting documents.", exception)
                }
        }
        else{
            adapter.replaceItems(TrackActivity.notStartedList)
            view.rv_notStarted_tasks.adapter = adapter
            view.rv_notStarted_tasks.layoutManager = LinearLayoutManager(context)
            view.rv_notStarted_tasks.hasFixedSize()
        }

        return view
    }

    class NotStartedAdapter: RecyclerView.Adapter<NotStartedAdapter.ItemViewHolder>(){
        class ItemViewHolder(itemView: View): RecyclerView.ViewHolder(itemView)

        private var tasks = mutableListOf<Task>()

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
            return ItemViewHolder(
                LayoutInflater.from(parent.context).inflate(
                    R.layout.not_started_item,
                    parent,
                    false
                )
            )
        }

        override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
            val data = tasks[position]
            holder.itemView.apply {
                tv_taskTitle.text = data.task
                tv_taskDescription.text = data.description
                }
            }

        override fun getItemCount(): Int = tasks.size

        fun replaceItems(tasks: MutableList<Task>) {
            this.tasks = tasks
            Log.w("ooppoopp", this.tasks.toString())
            notifyDataSetChanged()
        }
    }


}