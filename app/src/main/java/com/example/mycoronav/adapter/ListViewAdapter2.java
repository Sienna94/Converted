package com.example.mycoronav.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mycoronav.databinding.ItemRowBinding;
import com.example.mycoronav.viewmodel.SharedViewModel;
import com.example.mycoronav.vo.Row;

import java.util.ArrayList;

public class ListViewAdapter2 extends RecyclerView.Adapter<ListViewAdapter2.ViewHolder>{
    ItemRowBinding binding;
    public ArrayList<Row> rowItem = new ArrayList<>();

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        binding = ItemRowBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(rowItem.get(position));
    }

    @Override
    public int getItemCount() {
        return rowItem.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        private ItemRowBinding binding;

        public ViewHolder(ItemRowBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(Row rowItem){
            binding.setItem(rowItem);
            binding.delBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //
                }
            });
        }
    }
}
