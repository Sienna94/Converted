package com.example.mycoronav.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.mycoronav.databinding.ItemRowBinding
import com.example.mycoronav.vo.Row

class ListViewAdapter(context: Context) : RecyclerView.Adapter<ListViewAdapter.ViewHolder>() {
    lateinit var binding: ItemRowBinding
    var rowItem = mutableListOf<Row>()
    var onClickDel : ((Row) -> Unit) ?= null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        binding = ItemRowBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(rowItem[position])
    }

    override fun getItemCount(): Int {
        return rowItem.size
    }

    inner class ViewHolder(binding: ItemRowBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(rowItem: Row) {
            binding.item = rowItem
            binding.delBtn.setOnClickListener { onClickDel?.invoke(rowItem)}
        }
    }
}