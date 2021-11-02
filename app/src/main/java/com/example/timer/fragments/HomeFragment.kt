package com.example.timer.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.Navigation
import com.example.timer.R


class HomeFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_home, container, false)
        var endInstructionsButton = view.findViewById<Button>(R.id.btn_endInstructions)
        endInstructionsButton.setOnClickListener {
            Navigation.findNavController(view).navigate(R.id.action_homeFragment_to_listFragment)
        }
        return view
    }


}