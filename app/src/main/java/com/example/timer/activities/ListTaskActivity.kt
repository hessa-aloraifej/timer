package com.example.timer.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.timer.R

class ListTaskActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_task)


        //hide the action bar
        supportActionBar?.hide()


    }

}







