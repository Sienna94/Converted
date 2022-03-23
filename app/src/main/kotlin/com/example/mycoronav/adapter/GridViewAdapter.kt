package com.example.mycoronav.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.mycoronav.databinding.ItemRowGridBinding
import com.example.mycoronav.vo.Row

class GridViewAdapter(context: Context)
    : RecyclerView.Adapter<GridViewAdapter.ViewHolder>() {
    lateinit var binding : ItemRowGridBinding
    var rowItem = mutableListOf<Row>()
    var onClickDel : ((Row) -> Unit) ?= null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GridViewAdapter.ViewHolder {
        binding = ItemRowGridBinding.inflate(
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

    inner class ViewHolder(binding: ItemRowGridBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(rowItem: Row) {
            binding.item = rowItem
            binding.delBtn.setOnClickListener { onClickDel?.invoke(rowItem)}
        }
    }
}