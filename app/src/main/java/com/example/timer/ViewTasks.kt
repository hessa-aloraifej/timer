package com.example.timer

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class ViewTasks : AppCompatActivity() {
    private  val vm by lazy { ViewModelProvider(this).get(MyVM::class.java) }
    lateinit var myRV:RecyclerView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_tasks)
        myRV=findViewById(R.id.myRV)

        vm.getTask()
        vm.getNotes().observe(this, {
                list ->  myRV.adapter = RVAdapter(list)
            myRV.layoutManager = LinearLayoutManager(this)

        })
    }
}