package com.example.timer.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.timer.R
import kotlinx.android.synthetic.main.activity_home.*

class HomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        //hide the action bar
        supportActionBar?.hide()

        btn_home_track.setOnClickListener{
            startActivity(Intent(this, TrackActivity::class.java))
        }
        listTaskBtn.setOnClickListener{
            startActivity(Intent(this, ListTaskActivity::class.java))
        }
    }
}