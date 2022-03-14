package com.aldemir.listadapter.features.main.view

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.aldemir.listadapter.MainCoroutineRule
import com.aldemir.listadapter.data.Task
import com.aldemir.listadapter.features.main.repositories.FakeTaskRepository
import com.aldemir.listadapter.features.main.viewmodel.MainViewModel
import com.aldemir.listadapter.getOrAwaitValueTest
import com.aldemir.listadapter.utils.Status
import com.google.common.truth.Truth.assertThat
import junit.framework.TestCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
class MainViewModelTest: TestCase() {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    private lateinit var viewModel: MainViewModel

    @Before
    fun setup() {
        viewModel = MainViewModel(FakeTaskRepository())
    }

    private fun insertTask(task: Task) {
        viewModel.insertTask(task = task)
    }

    @Test
    fun `insert task with empty field, returns error`() {

        viewModel.insertTaskStatus(description = "")

        val value = viewModel.insertTaskStatus.getOrAwaitValueTest()

        assertThat(value.getContentIfNotHandled()?.status).isEqualTo(Status.ERROR)
    }

    @Test
    fun `insert task with too short name, returns error`() {

        viewModel.insertTaskStatus(description = "aa")

        val value = viewModel.insertTaskStatus.getOrAwaitValueTest()

        assertThat(value.getContentIfNotHandled()?.status).isEqualTo(Status.ERROR)
    }

    @Test
    fun `insert task in database, returns success`() {
        insertTask(Task(description = "Kotlin"))

        val value = viewModel.insertedId.getOrAwaitValueTest()

        assertThat(value.getContentIfNotHandled()?.status).isEqualTo(Status.SUCCESS)
    }

    @Test
    fun `delete task in database, returns success`() {
        insertTask(Task(id = 1, description = "Kotlin"))

        viewModel.deleteTask(Task(id = 1, description = "Kotlin"))

        val id = viewModel.deletedId.getOrAwaitValueTest()

        assertThat(id.getContentIfNotHandled()?.status).isEqualTo(Status.SUCCESS)

        viewModel.getAllTasks()

        val value = viewModel.tasks.getOrAwaitValueTest()

        assertThat(value.size).isEqualTo(0)
    }

    @Test
    fun `get task in database, returns success`() {
        insertTask(Task(description = "Kotlin"))
        insertTask(Task(description = "Java"))

        viewModel.getAllTasks()

        val value = viewModel.tasks.getOrAwaitValueTest()

        assertThat(value.size).isEqualTo(2)

    }
}