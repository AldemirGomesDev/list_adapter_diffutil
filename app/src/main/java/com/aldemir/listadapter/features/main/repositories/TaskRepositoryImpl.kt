package com.aldemir.listadapter.features.main.repositories

import androidx.lifecycle.LiveData
import com.aldemir.listadapter.data.Task
import com.aldemir.listadapter.data.TaskDao
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class TaskRepositoryImpl @Inject constructor(
    private val taskDao: TaskDao
): TaskRepository {

    override fun insert(task: Task): Long {
        return taskDao.insert(task)
    }

    override fun delete(task: Task): Int {
        return taskDao.delete(task)
    }

    override fun getAllTasks(): LiveData<List<Task>> {
        return taskDao.getAll()
    }

}