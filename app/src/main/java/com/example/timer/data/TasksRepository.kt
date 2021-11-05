package com.example.timer.data

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.CoroutineScope

class TasksRepository {
    private val TAG: String = "TaskRepository"
    private var db: FirebaseFirestore = FirebaseFirestore.getInstance()
    private val tasksList: MutableLiveData<List<Task>> = MutableLiveData()

    fun getTasksList(): LiveData<List<Task>> {
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
            }
            .addOnFailureListener { exception ->
                Log.w(TAG, "Error Saving Data.", exception)
            }

    }

    fun getTasks(): LiveData<List<Task>> {
        val tasks: ArrayList<Task> = arrayListOf()
        db.collection("Task")
            .get()
            .addOnSuccessListener { result ->

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
                    tasks.add(
                        Task(
                            document.id,
                            taskText,
                            descriptionText,
                            expectedTime,
                            spentTime,
                            state
                        )
                    )
                }
                tasksList.postValue(tasks)
            }
            .addOnFailureListener { exception ->
                Log.w(TAG, "Error getting documents.", exception)
            }

        return tasksList
    }

    fun updateTimer(taskId: String, spentTimer: String) {
        db.collection("Task").document(taskId).update("spentTime", spentTimer)
            .addOnSuccessListener { result ->
                Log.w(TAG, "State Updated Successfully!")
            }
            .addOnFailureListener { exception ->
                Log.w(TAG, "Error getting documents.", exception)
            }
    }

    fun updateState(taskId: String, state: String) {
        db.collection("Task").document(taskId).update("state", state)
            .addOnSuccessListener { result ->
                Log.w(TAG, "State Updated Successfully!")
            }
            .addOnFailureListener { exception ->
                Log.w(TAG, "Error getting documents.", exception)
            }

    }

    fun deleteTask(taskId: String) {
        db.collection("Task").document(taskId).delete()
            .addOnSuccessListener { result ->
                Log.w(TAG, "Deleted Successfully!")
            }
            .addOnFailureListener { exception ->
                Log.w(TAG, "Error getting documents.", exception)
            }
    }

    fun updateTask(taskId: String, task: String, description: String) {
        db.collection("Task").document(taskId).update("taskText", task)
            .addOnSuccessListener { result ->
                Log.w(TAG, "Title Updated Successfully!")
            }
            .addOnFailureListener { exception ->
                Log.w(TAG, "Error getting documents.", exception)
            }
        db.collection("Task").document(taskId).update("descriptionText", description)
            .addOnSuccessListener { result ->
                Log.w(TAG, "Description Updated Successfully!")
            }
            .addOnFailureListener { exception ->
                Log.w(TAG, "Error getting documents.", exception)
            }
    }
}