package com.example.timer

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView


class ListFragment : Fragment() {
    private  val vm by lazy { ViewModelProvider(this).get(MyVM::class.java) }
    lateinit var myRV: RecyclerView
    lateinit var addBtn:ImageButton


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view= inflater.inflate(R.layout.fragment_list, container, false)
        myRV=view.findViewById(R.id.myRV)
        addBtn=view.findViewById(R.id.addd)
        vm.getTask()
        vm.getNotes().observe(viewLifecycleOwner, {
                list ->  myRV.adapter = RVAdapter(list)
            myRV.layoutManager = LinearLayoutManager(activity)

        })
        addBtn.setOnClickListener {
            Navigation.findNavController(view).navigate(R.id.action_listFragment_to_addFragment)

        }


        return view
    }


}