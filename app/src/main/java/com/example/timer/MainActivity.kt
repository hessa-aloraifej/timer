package com.example.timer

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.navigation.Navigation

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        var endInstructionsButton = findViewById<Button>(R.id.btn_endInstructions)
        endInstructionsButton.setOnClickListener {
           intent= Intent(this,ListTaskActivity::class.java)
            startActivity(intent)
        }

        //hide the action bar
        supportActionBar?.hide()

    }

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