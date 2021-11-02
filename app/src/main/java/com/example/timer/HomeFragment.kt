package com.example.timer

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.Navigation


class HomeFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
       val view=  inflater.inflate(R.layout.fragment_home, container, false)
        var movebtn=view.findViewById<Button>(R.id.button3)
        movebtn.setOnClickListener {
            Navigation.findNavController(view).navigate(R.id.action_homeFragment_to_listFragment)

        }
        return view
    }


}