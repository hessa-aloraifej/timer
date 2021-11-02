package com.example.timer

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.timer.data.Task
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class TasksModelView (application: Application): AndroidViewModel(application) {
    var TAG: String = "IAmMainActivity"
    var db = Firebase.firestore
    val list: MutableLiveData<List<Task>> = MutableLiveData()
   // val tasks: ArrayList<Task> = arrayListOf()





    fun getNotes(): MutableLiveData<List<Task>> {
        return list
    }

    fun saveTask(task: String, description:String) {


        val task = hashMapOf(
            "taskText" to task,
            "descriptionText" to description

            )

        db.collection("Task").add(task)
            .addOnSuccessListener { Log.w("MainActivity", "Saving Data Successfully.")
       getTask()
            }
            .addOnFailureListener { exception ->
                Log.w(TAG, "Error Saving Data.", exception)
            }


    }
    fun getTask(){

        db.collection("Task")
            .get()
            .addOnSuccessListener { result ->
                var task_name =""
                var description= ""
                var tasks: ArrayList<Task> = arrayListOf()
                for (document in result) {

                    Log.d(TAG, "${document.id} => ${document.data}")
                    document.data.map { (key, value)
                        ->   when (key) {
                        "taskText" -> task_name = value as String
                        "descriptionText" -> description = value as String
                        }
                    }
                  tasks.add(Task(task_name,description,""))
                }
                list.postValue(tasks)


            }
            .addOnFailureListener { exception ->
                Log.w(TAG, "Error getting documents.", exception)
            }



            }

    }





 //   fun readFireStoreData() {
    //        val db = FirebaseFirestore.getInstance()
    //        db.collection("Task")
    //            .get()
    //            .addOnCompleteListener {
    //
    //                val result: StringBuffer = StringBuffer()
    //
    //                if(it.isSuccessful) {
    //                    for(document in it.result!!) {
    //                        val taskn=result.append(document.data.getValue("taskText")).append().toString()
    //                        val taskdes= result.append(document.data.getValue("descriptionText")).append().toString()
    //
    //                        tasks.add(Task(document.id,taskn,taskdes))
    //                    }
    //
    //                }
    //            }
    //
    //    }




