package com.example.timer

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.timer.data.Task
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class TasksModelView(application: Application) : AndroidViewModel(application) {

    private val TAG: String = "TasksViewModel"
    private var db = Firebase.firestore
    private val tasksList: MutableLiveData<List<Task>> = MutableLiveData()

    fun getTasksList(): MutableLiveData<List<Task>> {
        return tasksList
    }

    fun saveTask(
        taskId: String,
        task: String,
        description: String,
        expectedTime: String,
        spentTime: String,
    ) {

        val task = hashMapOf(
            "taskId" to taskId,
            "taskText" to task,
            "descriptionText" to description,
            "expectedTime" to expectedTime,
            "spentTime" to spentTime,
            "state" to "notStarted",
        )

        db.collection("Task").add(task)
            .addOnSuccessListener {
                Log.w(TAG, "Saving Data Successfully.")
                getTasks()
            }
            .addOnFailureListener { exception ->
                Log.w(TAG, "Error Saving Data.", exception)
            }
    }

    fun getTasks() {

        db.collection("Task")
            .get()
            .addOnSuccessListener { result ->

                var tasks: ArrayList<Task> = arrayListOf()

                var taskText = ""
                var descriptionText = ""
                var expectedTime = ""
                var spentTime = ""
                var state = ""

                for (document in result) {
                    Log.d(TAG, "${document.id} => ${document.data}")
                    document.data.map { (key, value)
                        ->
                        when (key) {
                            "taskText" -> taskText = value as String
                            "descriptionText" -> descriptionText = value as String
                            "expectedTime" -> expectedTime = value as String
                            "spentTime" -> spentTime = value as String
                            "state" -> state = value as String
                        }
                    }
                    tasks.add(Task(document.id, taskText, descriptionText, expectedTime, spentTime,state))
                }
                tasksList.postValue(tasks)
            }
            .addOnFailureListener { exception ->
                Log.w(TAG, "Error getting documents.", exception)
            }


    }

    fun updateTimer(taskId: String, spentTimer: String) {

        db.collection("Task")
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    if (document.id == taskId) {
                        db.collection("Task").document(taskId).update("spentTime", spentTimer)
                        Log.w(TAG, "Updated Spent Timer Successfully.")
                    }
                }
                getTasks()
            }
            .addOnFailureListener { exception ->
                Log.w(TAG, "Error getting documents.", exception)
            }
    }

    fun updateState(taskId: String, state: String) {

        db.collection("Task")
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    if (document.id == taskId) {
                        db.collection("Task").document(taskId).update("state", state)
                        Log.w(TAG, "Updated Spent Timer Successfully.")
                    }
                }
                getTasks()
            }
            .addOnFailureListener { exception ->
                Log.w(TAG, "Error getting documents.", exception)
            }
    }

}










