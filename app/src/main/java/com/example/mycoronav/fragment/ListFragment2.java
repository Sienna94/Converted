package com.example.mycoronav.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.mycoronav.adapter.ListViewAdapter2;
import com.example.mycoronav.databinding.FragmentListBinding;
import com.example.mycoronav.viewmodel.SharedViewModel2;
import com.example.mycoronav.vo.Row;

import java.util.ArrayList;

public class ListFragment2 extends Fragment {
    private FragmentListBinding binding;
    //adapter
    private ListViewAdapter2 listViewAdapter;
    private LinearLayoutManager linearLayoutManager;
    private SharedViewModel2 sharedViewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        listViewAdapter = new ListViewAdapter2();
        sharedViewModel = new SharedViewModel2();
        //옵저빙하는 모든 페이지에 영향을 미칠 수도 있기 때문에 잘 고려해야함.
        //ex_ eventbus의 사이드이펙트
        binding = FragmentListBinding.inflate(inflater, container, false);
        binding.swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                binding.swipeRefresh.setRefreshing(false);
            }
        });
        setList();

        return binding.getRoot();
    }
    // 어댑터 및 레이아웃 매니저 설정 -> onCreate 에서 한번만
    private void setList(){
        //어댑터 및 manager 등록은 한번만 하도록 변경할 것.
        //어댑터와 리사이클러뷰는 특별한 경우가 아니면
        //한번만 바인딩하도록 한다. 이미 추가된 어댑터를 재활용하도록.
        //layout manager도 미리 생성해놓고 달아줄 것.
        //scroll listener도 마찬가지.
        listViewAdapter.setOnDelClickListener(new ListViewAdapter2.OnDelClickListener() {
            @Override
            public void onItemDelClick(Row rowItem, int position) {
                sharedViewModel.deleteRow(rowItem);
                listViewAdapter.notifyItemRemoved(position);
            }
        });
        linearLayoutManager = new LinearLayoutManager(requireContext());

        binding.rvListView.setAdapter(listViewAdapter);
        binding.rvListView.setLayoutManager(linearLayoutManager);

        binding.rvListView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if(binding.rvListView.canScrollVertically(1)){
//                    sharedViewModel.loadMore();
                }
            }
        });
    }
    // data 변화 -> UI 업데이트시 타 프래그먼트에서 변화가 생기면 어떻게 반영해야하는지 질문
    // MainActivity에서 실행
    public void updateList(ArrayList<Row> rowItem){
        listViewAdapter.rowItem = rowItem;
        binding.rvListView.getAdapter().notifyDataSetChanged();
    }
}