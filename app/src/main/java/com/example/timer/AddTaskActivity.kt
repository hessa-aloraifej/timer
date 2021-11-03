package com.example.timer

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.lifecycle.ViewModelProvider


class AddTaskActivity : AppCompatActivity() {
    private  val tasksViewModel by lazy { ViewModelProvider(this).get(TasksModelView::class.java) }

    lateinit var taskNameEditText: EditText
    lateinit var taskDescriptionEditText: EditText
    lateinit var taskExpectedTimeEditText: EditText
    lateinit var addButton: Button
    lateinit var viewTasksButton: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_add_task)
        taskNameEditText = findViewById(R.id.edt_taskName)
        taskDescriptionEditText = findViewById(R.id.edt_taskDescription)
        taskExpectedTimeEditText = findViewById(R.id.edt_taskExpectedTime)

        addButton = findViewById(R.id.btn_add)
        viewTasksButton =findViewById(R.id.btn_viewTasks)

        addButton.setOnClickListener {
            tasksViewModel.saveTask("",taskNameEditText.text.toString(),taskDescriptionEditText.text.toString(),taskExpectedTimeEditText.text.toString(),"")
            clearFields()

        }

        viewTasksButton.setOnClickListener {
           val intent= Intent(this,ListTaskActivity::class.java)
            startActivity(intent)
        }
    }

    private fun clearFields(){
        taskNameEditText.text.clear()
        taskDescriptionEditText.text.clear()
        taskExpectedTimeEditText.text.clear()
    }
}