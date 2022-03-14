package com.aldemir.listadapter.data

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface TaskDao {
    @Query("SELECT * FROM task")
    fun getAll(): LiveData<List<Task>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(user: Task): Long

    @Delete
    fun delete(user: Task): Int
}