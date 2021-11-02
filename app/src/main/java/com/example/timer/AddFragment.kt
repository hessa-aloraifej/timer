package com.example.timer

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation


class AddFragment : Fragment() {
    private  val vm by lazy { ViewModelProvider(this).get(MyVM::class.java) }
    lateinit var taskName: EditText
    lateinit var taskDescription: EditText
    lateinit var addBtn: Button
    lateinit var viewBtn: Button



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view= inflater.inflate(R.layout.fragment_add, container, false)
        taskName=view.findViewById(R.id.taskName)
        taskDescription=view.findViewById(R.id.taskDescription)
        addBtn=view.findViewById(R.id.button)
        viewBtn=view.findViewById(R.id.button2)

        addBtn.setOnClickListener {
            vm.save(taskName.text.toString(),taskDescription.text.toString())
            taskName.text.clear()
            taskDescription.text.clear()

        }
        viewBtn.setOnClickListener {
            Navigation.findNavController(view).navigate(R.id.action_addFragment_to_listFragment)
        }
        return view
    }


}