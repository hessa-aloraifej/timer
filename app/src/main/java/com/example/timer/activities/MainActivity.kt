package com.example.timer.activities

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.viewpager2.widget.ViewPager2
import com.example.timer.R
import com.example.timer.adapters.ViewPagerAdapter
import com.example.timer.data.SlideData
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var sharedPreferences: SharedPreferences
    private val slidesList = mutableListOf<SlideData>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //hide the action bar
        supportActionBar?.hide()

        //getCoffee()
        sharedPreferences = getSharedPreferences("com.example.timer", MODE_PRIVATE)
        val readInstructions = sharedPreferences.getInt("read", 0)

        if (readInstructions == 0) {
            main_screen.visibility = View.VISIBLE
            generate()

            viewPager.adapter = ViewPagerAdapter(slidesList, this)
            viewPager.orientation = ViewPager2.ORIENTATION_HORIZONTAL
            indicator.setViewPager(viewPager)

        } else {
            startActivity(Intent(this, HomeActivity::class.java))
        }

    }

    fun skipOrFinish() {
        sharedPreferences.edit().putInt("read", 1).apply()
        startActivity(Intent(this, HomeActivity::class.java))
        finish()
    }

    private fun addSlide(slide: SlideData) {
        slidesList.add(slide)
    }

    private fun generate() {
        addSlide(SlideData("Add Your Own Task", R.drawable.slide1, "Stay organized by logging your tasks"))
        addSlide(SlideData("Set Your Timers", R.drawable.slide2, "Stay focused by setting timers to your tasks"))
        addSlide(SlideData("Track Your Progress", R.drawable.slide3, "Become goal-oriented by tracking your progress"))
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}