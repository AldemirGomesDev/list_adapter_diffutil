package com.aldemir.listadapter.features.main.repositories

import androidx.lifecycle.LiveData
import com.aldemir.listadapter.data.Task

interface TaskRepository {
    fun insert(task: Task): Long
    fun delete(task: Task): Int
    fun getAllTasks(): LiveData<List<Task>>
}