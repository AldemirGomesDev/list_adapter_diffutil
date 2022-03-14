package com.aldemir.listadapter.features.main.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aldemir.listadapter.data.Task
import com.aldemir.listadapter.features.main.repositories.TaskRepository
import com.aldemir.listadapter.utils.Constants
import com.aldemir.listadapter.utils.Event
import com.aldemir.listadapter.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
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

    private val _deletedId = MutableLiveData<Event<Resource<Int>>>()
    val deletedId: LiveData<Event<Resource<Int>>> = _deletedId

    private val _insertedId = MutableLiveData<Event<Resource<Long>>>()
    val insertedId: LiveData<Event<Resource<Long>>> = _insertedId

    private val _insertTaskStatus = MutableLiveData<Event<Resource<Task>>>()
    val insertTaskStatus: LiveData<Event<Resource<Task>>> = _insertTaskStatus

    fun getAllTasks() {
        viewModelScope.launch {
            tasks = repository.getAllTasks()
        }
    }

    fun insertTask(task: Task) = viewModelScope.launch(Dispatchers.IO) {
        val id = repository.insert(task = task)
        if (id == 1L) {
            _insertedId.postValue(Event(Resource.success(id)))
        } else {
            _insertedId.postValue(Event(Resource.error("Error", null)))
        }

    }

    fun insertTaskStatus(description: String) {
        if (description.isEmpty()) {
            _insertTaskStatus.postValue(Event(Resource.error("The fields must not be empty", null)))
            return
        }
        if (description.length < Constants.MIN_NAME_LENGTH) {
            _insertTaskStatus.postValue(
                Event(
                    Resource.error(
                        "The name of the item" +
                                "must not exceed ${Constants.MIN_NAME_LENGTH} characters", null
                    )
                )
            )
            return
        }
        val shoppingItem = Task(
            description = description
        )
        insertTask(shoppingItem)
        _insertTaskStatus.postValue(Event(Resource.success(shoppingItem)))
    }

    fun deleteTask(task: Task) = viewModelScope.launch(Dispatchers.IO) {
        val id = repository.delete(task = task)
        if (id == 1) {
            _deletedId.postValue(Event(Resource.success(id)))
        } else {
            _deletedId.postValue(Event(Resource.error("Error", null)))
        }
    }
}