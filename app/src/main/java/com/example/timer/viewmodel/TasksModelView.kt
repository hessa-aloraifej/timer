package com.example.timer.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.timer.data.Task
import com.example.timer.data.TasksRepository
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
            repository.updateState(taskId,state)
        }
    }

    fun updateTask(taskId: String, task: String, description: String){
        viewModelScope.launch(Dispatchers.IO) {
            repository.updateTask(taskId,task,description)
        }
    }

    fun deleteTask(taskId: String){
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteTask(taskId)
        }
    }

}










