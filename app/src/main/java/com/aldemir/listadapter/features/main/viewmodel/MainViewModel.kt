package com.aldemir.listadapter.features.main.viewmodel

import android.util.Log
import androidx.lifecycle.*
import com.aldemir.listadapter.data.Task
import com.aldemir.listadapter.features.main.repositories.TaskRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: TaskRepository
) : ViewModel() {

    companion object {
        const val TAG = "ViewModelMain"
    }

    private val _tasks = MutableLiveData<List<Task>>()
    var tasks: LiveData<List<Task>> = _tasks

    fun getAllTasks() {
        viewModelScope.launch {
            tasks = repository.getAllTasks()
            Log.w(TAG, "Atualizando UI")
        }
    }

    fun insertTask(task: Task) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.insert(task = task)
        }
    }

    fun deleteTask(task: Task) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.delete(task = task)
        }
    }
}