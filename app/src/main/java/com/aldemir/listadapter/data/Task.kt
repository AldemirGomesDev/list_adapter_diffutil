package com.aldemir.listadapter.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(tableName = "task", indices = [Index(value = ["description"], unique = true) ])
data class Task(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val id: Int = 0,
    @ColumnInfo(name = "description")
    var description: String? = "",
    @ColumnInfo(name = "title")
    val isDone: Boolean? = false
)