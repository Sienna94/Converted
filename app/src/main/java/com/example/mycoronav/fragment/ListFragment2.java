package com.example.mycoronav.fragment;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.mycoronav.R;
import com.example.mycoronav.adapter.ListViewAdapter;
import com.example.mycoronav.adapter.ListViewAdapter2;
import com.example.mycoronav.databinding.FragmentListBinding;
import com.example.mycoronav.viewmodel.SharedViewModel;
import com.example.mycoronav.vo.Row;

import java.util.ArrayList;

public class ListFragment2 extends Fragment {
    private FragmentListBinding binding;
    //adapter
    private ListViewAdapter2 listViewAdapter;
    private LinearLayoutManager linearLayoutManager;
    private SharedViewModel sharedViewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
//        listViewAdapter = new ListViewAdapter(requireContext());
        listViewAdapter = new ListViewAdapter2();
        sharedViewModel = new SharedViewModel();
        //옵저빙하는 모든 페이지에 영향을 미칠 수도 있기 때문에 잘 고려해야함.
        //ex_ eventbus의 사이드이펙트
        binding = FragmentListBinding.inflate(inflater, container, false);
        binding.swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                binding.swipeRefresh.setRefreshing(false);
            }
        });
        return binding.getRoot();
    }

    public void setList(ArrayList<Row> rowItem){
//        listViewAdapter.setRowItem(rowItem);
        listViewAdapter.rowItem = rowItem;
//        listViewAdapter.setOnClickDel();
        linearLayoutManager = new LinearLayoutManager(requireContext());

        binding.rvListView.setAdapter(listViewAdapter);
        binding.rvListView.setLayoutManager(linearLayoutManager);

        //어댑터와 리사이클러뷰는 특별한 경우가 아니면
        //한번만 바인딩하도록 한다. 이미 추가된 어댑터를 재활용하도록.
        //layout manager도 미리 생성해놓고 달아줄 것.
        //scroll listener도 마찬가지.

        //onclickdel HOF 처리 필요

        binding.rvListView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if(binding.rvListView.canScrollVertically(1)){
                    sharedViewModel.loadMore();
                }
            }
        });
    }

}