package com.example.timer.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.timer.R
import com.example.timer.TrackActivity
import com.example.timer.data.Task
import kotlinx.android.synthetic.main.fragment_completed.*
import kotlinx.android.synthetic.main.fragment_completed.view.*
import kotlinx.android.synthetic.main.fragment_not_started.*
import kotlinx.android.synthetic.main.fragment_started.*
import kotlinx.android.synthetic.main.fragment_started.view.*
import kotlinx.android.synthetic.main.item_row.view.*

class CompletedFragment: Fragment() {

    private lateinit var adapter: CompletedAdapter


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_completed, container, false)

        adapter = CompletedAdapter()

        adapter.replaceItems(TrackActivity.completedList)
        view.rv_completed_tasks.adapter = adapter
        view.rv_completed_tasks.layoutManager = LinearLayoutManager(context)
        view.rv_completed_tasks.hasFixedSize()

        return view
    }

    class CompletedAdapter: RecyclerView.Adapter<CompletedAdapter.ItemViewHolder>(){
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