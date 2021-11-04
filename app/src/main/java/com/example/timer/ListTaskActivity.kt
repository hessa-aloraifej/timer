package com.example.timer

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.timer.adapters.TasksAdapter

class ListTaskActivity : AppCompatActivity() {
    private val tasksViewModel by lazy { ViewModelProvider(this).get(TasksModelView::class.java) }

    lateinit var recyclerView: RecyclerView
    lateinit var tasksAdapter: TasksAdapter
    lateinit var addTaskButton: ImageButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_task)


        recyclerView = findViewById(R.id.rv_tasks)
        tasksAdapter = TasksAdapter()
        recyclerView.adapter = tasksAdapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        addTaskButton = findViewById(R.id.btn_addTask)

        tasksViewModel.getTasks()
        tasksViewModel.getTasksList().observe(this, { list ->
            tasksAdapter.setData(list)
        })

        addTaskButton.setOnClickListener {
            addTaskAlert()
        }
    }


    fun addTaskAlert() {

        val builder = AlertDialog.Builder(this)

        val view = layoutInflater.inflate(R.layout.add_task_alert, null)

        builder.setView(view)

        val alertDialog: AlertDialog = builder.create()

        val taskNameAlert = view.findViewById<EditText>(R.id.edt_taskNameAlert)
        val taskDescriptionAlert = view.findViewById<EditText>(R.id.edt_taskDescriptionAlert)
        //val taskExpectedTimeAlert = view.findViewById<EditText>(R.id.edt_taskExpectedTimeAlert)
        val seekBarTimer = view.findViewById<SeekBar>(R.id.seekBarTimer)
        val tvExpectedTimeTask = view.findViewById<TextView>(R.id.tvExpectedTimeTask)

        val addBtnAlert = view.findViewById<Button>(R.id.btn_addAlert)

        taskNameAlert.setHint("Enter Task Name")
        taskDescriptionAlert.setHint("Enter Task Description")
        //taskExpectedTimeAlert.setHint("Enter Task Expected Time As Minutes")

        alertDialog.show()

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
            var taskName = taskNameAlert.text.toString()
            var taskDescription = taskDescriptionAlert.text.toString()
            // var taskExpectedTime=taskExpectedTimeAlert.text.toString()
            var taskExpectedTime = tvExpectedTimeTask.text.toString()
            var expectedNum = taskExpectedTime.toLong() * 1000 * 60


            if (taskName.isNotEmpty() && taskDescription.isNotEmpty() && taskExpectedTime.isNotEmpty()) {
                tasksViewModel.saveTask(
                    "",
                    taskName,
                    taskDescription,
                    taskExpectedTime,
                    expectedNum.toString()
                )
            } else {
                Toast.makeText(this, "You Should Complete All Information", Toast.LENGTH_SHORT)
                    .show()
            }


            alertDialog.dismiss()

        }

    }

    override fun onResume() {
        super.onResume()
        tasksViewModel.getTasksList().observe(this, { list ->
            tasksAdapter.setData(list)
        })
    }


}







