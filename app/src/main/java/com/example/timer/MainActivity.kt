package com.example.timer

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.viewpager2.widget.ViewPager2
import com.example.timer.adapters.ViewPagerAdapter
import com.example.timer.data.SlideData
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    //    getCoffee() --------------------------
    private lateinit var sharedPreferences: SharedPreferences
    private val slides = mutableListOf<SlideData>()
//    -------------------------------------


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //hide the action bar
        supportActionBar?.hide()

        sharedPreferences = getSharedPreferences("getCoffee", MODE_PRIVATE)

        val readInstructions = sharedPreferences.getInt("read", 0)

        if (readInstructions > 0) {
            main_screen.visibility = View.VISIBLE
            generate()

            viewPager.adapter = ViewPagerAdapter(slides, this)
            viewPager.orientation = ViewPager2.ORIENTATION_HORIZONTAL

            indicator.setViewPager(viewPager)
        } else {
            startActivity(Intent(this, HomeActivity::class.java))
        }

    }

    //    getCoffee() --------------------------------------
    fun skipOrFinish() {
        sharedPreferences.edit().putInt("read", 1).apply()
        startActivity(Intent(this, HomeActivity::class.java))
        finish()
    }

    private fun addSlide(slide: SlideData) {
        slides.add(slide)
    }

    private fun generate() {
        for (i in 0..5)
            addSlide(SlideData(i.toString(), R.drawable.icon_add, i.toString()))
    }

//    --------------------------------------------------------

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}

//    <androidx.fragment.app.FragmentContainerView
//        android:id="@+id/fragmentContainerView"
//        android:name="androidx.navigation.fragment.NavHostFragment"
//        android:layout_width="match_parent"
//        android:layout_height="match_parent"
//        app:defaultNavHost="true"
//        app:layout_constraintBottom_toBottomOf="parent"
//        app:layout_constraintEnd_toEndOf="parent"
//        app:layout_constraintStart_toStartOf="parent"
//        app:layout_constraintTop_toTopOf="parent"
//        app:navGraph="@navigation/fragments" />