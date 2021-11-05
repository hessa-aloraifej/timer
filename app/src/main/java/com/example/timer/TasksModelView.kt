package com.example.timer

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.timer.data.Task
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class TasksModelView(application: Application) : AndroidViewModel(application) {

    val getTasksList : LiveData<List<Task>>
    private val repository : TasksRepository

    init {
        repository = TasksRepository()
        getTasksList = repository.getTasksList()
    }

    fun getAllTasks(){
        viewModelScope.launch(Dispatchers.IO) {
            repository.getTasks()
        }
    }
    fun saveTask(
                taskId: String,
        task: String,
        description: String,
        expectedTime: String,
        spentTime: String,
    ){
        viewModelScope.launch(Dispatchers.IO) {
            repository.saveTask(taskId,task,description, expectedTime, spentTime)
        }
    }

    fun updateTimer(taskId: String, spentTimer: String){
        viewModelScope.launch(Dispatchers.IO) {
            repository.updateTimer(taskId,spentTimer)
        }
    }


    fun updateState(taskId: String, state: String){
        viewModelScope.launch(Dispatchers.IO) {
            repository.updateTimer(taskId,state)
        }
    }

//
//    private val TAG: String = "TasksViewModel"
//    private var db: FirebaseFirestore = FirebaseFirestore.getInstance()
//    private val tasksList: MutableLiveData<List<Task>> = MutableLiveData()
//
//    fun getTasksList(): MutableLiveData<List<Task>> {
//        return tasksList
//    }
//
//    fun saveTask(
//        taskId: String,
//        task: String,
//        description: String,
//        expectedTime: String,
//        spentTime: String,
//    ) {
//        viewModelScope.launch(Dispatchers.IO) {
//            val task = hashMapOf(
//                "taskId" to taskId,
//                "taskText" to task,
//                "descriptionText" to description,
//                "expectedTime" to expectedTime,
//                "spentTime" to spentTime,
//                "state" to "notStarted",
//            )
//
//            db.collection("Task").add(task)
//                .addOnSuccessListener {
//                    Log.w(TAG, "Saving Data Successfully.")
//                    getTasks()
//                }
//                .addOnFailureListener { exception ->
//                    Log.w(TAG, "Error Saving Data.", exception)
//                }
//        }
//    }
//
//    fun getTasks(): LiveData<List<Task>> {
//        val tasks: ArrayList<Task> = arrayListOf()
//        viewModelScope.launch(Dispatchers.IO) {
//            db.collection("Task")
//                .get()
//                .addOnSuccessListener { result ->
//
//
//                    var taskText = ""
//                    var descriptionText = ""
//                    var expectedTime = ""
//                    var spentTime = ""
//                    var state = ""
//
//                    for (document in result) {
//                        Log.d(TAG, "${document.id} => ${document.data}")
//                        document.data.map { (key, value)
//                            ->
//                            when (key) {
//                                "taskText" -> taskText = value as String
//                                "descriptionText" -> descriptionText = value as String
//                                "expectedTime" -> expectedTime = value as String
//                                "spentTime" -> spentTime = value as String
//                                "state" -> state = value as String
//                            }
//                        }
//                        tasks.add(
//                            Task(
//                                document.id,
//                                taskText,
//                                descriptionText,
//                                expectedTime,
//                                spentTime,
//                                state
//                            )
//                        )
//                    }
//                    tasksList.postValue(tasks)
//                }
//                .addOnFailureListener { exception ->
//                    Log.w(TAG, "Error getting documents.", exception)
//                }
//
//        }
//        return tasksList
//    }
//
//    fun updateTimer(taskId: String, spentTimer: String) {
//        viewModelScope.launch(Dispatchers.IO) {
//            db.collection("Task").document(taskId).update("spentTime",spentTimer)
//                .addOnSuccessListener { result ->
//                    Log.w(TAG, "State Updated Successfully!")
//                }
//                .addOnFailureListener { exception ->
//                    Log.w(TAG, "Error getting documents.", exception)
//                }
//        }
//    }
//
//    fun updateState(taskId: String, state: String) {
//        viewModelScope.launch(Dispatchers.IO) {
//            db.collection("Task").document(taskId).update("state",state)
//                .addOnSuccessListener { result ->
//                    Log.w(TAG, "State Updated Successfully!")
//                }
//                .addOnFailureListener { exception ->
//                    Log.w(TAG, "Error getting documents.", exception)
//                }
//        }
//    }

}










