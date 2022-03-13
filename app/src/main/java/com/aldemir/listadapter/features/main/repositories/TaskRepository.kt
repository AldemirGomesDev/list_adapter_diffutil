package com.aldemir.listadapter.features.main.repositories

import androidx.lifecycle.LiveData
import com.aldemir.listadapter.data.Task

interface TaskRepository {
    fun insert(task: Task)
    fun delete(task: Task)
    fun getAllTasks(): LiveData<List<Task>>
}