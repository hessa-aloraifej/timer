package com.example.timer.activities

import android.os.Bundle
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.timer.R
import com.example.timer.data.Task
import com.example.timer.fragments.CompletedFragment
import com.example.timer.fragments.NotStartedFragment
import com.example.timer.fragments.StartedFragment
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_track.*
class TrackActivity : AppCompatActivity() {

    companion object{
        var db = Firebase.firestore
        var getData = false
        var notStartedListObj = mutableListOf<Task>()
        var startedListObj = mutableListOf<Task>()
        var completedListObj = mutableListOf<Task>()
    }

    private lateinit var completed: CompletedFragment
    private  var completedList = mutableListOf<Task>()

    private lateinit var started: StartedFragment
    private var startedList = mutableListOf<Task>()

    private lateinit var notStarted: NotStartedFragment
    private var notStartedList = mutableListOf<Task>()

    lateinit var backButton: ImageButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_track)

        //hide the action bar
        supportActionBar?.hide()

        backButton = findViewById(R.id.backBtn_trackActivity)
        backButton.setOnClickListener{
            getData = false
            startedListObj = mutableListOf()
            completedListObj = mutableListOf()
            notStartedListObj = mutableListOf()
            finish()

        }

        completed = CompletedFragment()
        started = StartedFragment()
        notStarted = NotStartedFragment()

        replaceFragment(notStarted, notStartedList)

        bottomNavigation_track.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.btn_menu_completed -> replaceFragment(completed,
                    completedList
                )
                R.id.btn_menu_notCompleted -> replaceFragment(started, startedList)
                R.id.btn_menu_notStarted -> replaceFragment(notStarted, notStartedList)
            }
            true
        }

    }

    private fun replaceFragment(fragment: Fragment, list: MutableList<Task>) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.fragment_track, fragment)
        transaction.commit()
    }
}