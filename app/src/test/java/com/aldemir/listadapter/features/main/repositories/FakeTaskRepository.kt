package com.aldemir.listadapter.features.main.repositories

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.aldemir.listadapter.data.Task

class FakeTaskRepository: TaskRepository {

    private val tasks = mutableListOf<Task>()

    private val observableTasks = MutableLiveData<List<Task>>(tasks)

    private fun refreshLiveData() {
        observableTasks.postValue(tasks)
    }

    override fun insert(task: Task): Long {
        tasks.add(task)
        refreshLiveData()
        return 1
    }

    override fun delete(task: Task): Int {
        tasks.remove(task)
        refreshLiveData()
        return 1
    }

    override fun getAllTasks(): LiveData<List<Task>> {
        return observableTasks
    }
}