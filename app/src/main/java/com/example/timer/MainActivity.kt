package com.example.timer

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Layout
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import androidx.cardview.widget.CardView
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider

class MainActivity : AppCompatActivity() {
    private  val vm by lazy { ViewModelProvider(this).get(MyVM::class.java) }
    lateinit var taskName:EditText
    lateinit var taskDescription:EditText
    lateinit var addBtn: Button
    lateinit var viewBtn: Button
    lateinit var startbtn:Button
    lateinit var layoutMain:LinearLayout
    lateinit var cardView:CardView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        taskName=findViewById(R.id.taskName)
        taskDescription=findViewById(R.id.taskDescription)
        addBtn=findViewById(R.id.button)
        viewBtn=findViewById(R.id.button2)
        startbtn=findViewById(R.id.button4)
        layoutMain=findViewById(R.id.lMain)
        cardView=findViewById(R.id.cardView)
        startbtn.setOnClickListener {
            layoutMain.isVisible=true
            cardView.isVisible=false
        }
        addBtn.setOnClickListener {
            vm.save(taskName.text.toString(),taskDescription.text.toString())
            taskName.text.clear()
            taskDescription.text.clear()

        }
        viewBtn.setOnClickListener {
            val intent= Intent(this,ViewTasks::class.java)
            startActivity(intent)
        }
    }
}