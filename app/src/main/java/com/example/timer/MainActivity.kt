package com.example.timer

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //hide the action bar
        supportActionBar?.hide()

    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}