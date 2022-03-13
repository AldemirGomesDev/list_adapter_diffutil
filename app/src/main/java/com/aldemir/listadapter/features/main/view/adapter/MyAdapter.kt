package com.aldemir.listadapter.features.main.view.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.aldemir.listadapter.data.Task
import com.aldemir.listadapter.databinding.ItemListAccountBinding

class MyAdapter : ListAdapter<Task, MyAdapter.ViewHolder>(MyDiffCallback()) {

    companion object {
        const val TAG = "MyAdapter"
    }

    lateinit var mClickListener: ClickListener

    fun setOnItemClickListener(aClickListener: ClickListener) {
        mClickListener = aClickListener
    }

    interface ClickListener {
        fun onClickDelete(position: Int, aView: View)
    }

    class MyDiffCallback : DiffUtil.ItemCallback<Task>() {

        override fun areItemsTheSame(
            oldItem: Task,
            newItem: Task
        ) = oldItem.id == newItem.id

        override fun areContentsTheSame(
            oldItem: Task,
            newItem: Task
        ) = oldItem == newItem

    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val binding = ItemListAccountBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: ViewHolder,
        position: Int
    ) {
        val new = currentList[position]
        holder.binding.tvNameAccount.text = new.description
    }

    inner class ViewHolder(val binding: ItemListAccountBinding) :
        RecyclerView.ViewHolder(binding.root),
        View.OnClickListener {

        override fun onClick(p0: View?) {
            Log.w(TAG, "Clicou no item: $adapterPosition")
        }

        init {
            binding.root.setOnClickListener(this)
            binding.btnRemove.setOnClickListener {
                mClickListener.onClickDelete(adapterPosition, it)
            }
        }


    }

}