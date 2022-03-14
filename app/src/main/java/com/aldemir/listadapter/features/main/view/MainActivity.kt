package com.aldemir.listadapter.features.main.view

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.aldemir.listadapter.features.main.view.adapter.MyAdapter
import com.aldemir.listadapter.R
import com.aldemir.listadapter.data.Task
import com.aldemir.listadapter.databinding.ActivityScrollingBinding
import com.aldemir.listadapter.features.main.viewmodel.MainViewModel
import com.aldemir.listadapter.utils.Constants
import com.aldemir.listadapter.utils.Resource
import com.aldemir.listadapter.utils.Status
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity(), MyAdapter.ClickListener {

    companion object {
        const val TAG = "ActivityMain"
    }

    private lateinit var binding: ActivityScrollingBinding
    private lateinit var adapter: MyAdapter
    private val viewModel: MainViewModel by viewModels()
    private var list: ArrayList<Task> = arrayListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityScrollingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(findViewById(R.id.toolbar))

        setupRecyclerView()
        getTasks()
        observers()
        listeners()

    }

    private fun listeners() {
        binding.btnAddAccount.setOnClickListener {
            insertTask()
        }
    }

    private fun insertTask() {
        val taskDescription = binding.edtName.text.toString()
        viewModel.insertTaskStatus(taskDescription)
        binding.edtName.setText("")

    }

    private fun observers() {
        viewModel.tasks.observe(this, Observer { tasks ->
            list = arrayListOf()
            val tasksSorted = tasks.sortedBy { it.description }
            list.addAll(tasksSorted)
            Log.w(TAG, "Lista Ordenada: ${list.size}")
            adapter.submitList(list)
        })

        viewModel.insertTaskStatus.observe(this, Observer { tasks ->
            when (tasks.getContentIfNotHandled()?.status) {
                Status.SUCCESS -> {
                    Toast.makeText(this,
                        "Task inserted with success",
                        Toast.LENGTH_SHORT).show()
                }
                Status.ERROR -> {
                    Toast.makeText(this,
                        "${tasks.peekContent().message}",
                        Toast.LENGTH_SHORT).show()
                }
                else -> {
                    Toast.makeText(this,
                        "${tasks.peekContent().message}",
                        Toast.LENGTH_SHORT).show()
                }
            }
        })
    }

    private fun getTasks() {
        Log.w(TAG, "getAllTasks")
        viewModel.getAllTasks()
    }

    private fun deleteTask(position: Int) {
        viewModel.deleteTask(list[position])
    }

    private fun setupRecyclerView() {
        val layoutManager = LinearLayoutManager(this)
        binding.recyclerViewList.layoutManager = layoutManager
        adapter = MyAdapter()
        binding.recyclerViewList.adapter = adapter
        adapter.setOnItemClickListener(this)
    }

    override fun onClickDelete(position: Int, aView: View) {
        deleteTask(position = position)
    }
}