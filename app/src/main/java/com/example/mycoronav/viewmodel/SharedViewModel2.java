package com.example.mycoronav.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.mycoronav.common.Constants;
import com.example.mycoronav.repository.RepositoryImpl;
import com.example.mycoronav.vo.Row;

import java.util.ArrayList;

public class SharedViewModel2 extends ViewModel {
    //livedata
    public MutableLiveData<ArrayList<Row>> rows_live = new MutableLiveData<>();
    private RepositoryImpl repository;
    private int page = 1;

    public void getRows(){
        repository.getHospitalItem(Constants.START_PAGE);
//        repository.setOnReturn( rows ->
//
//        );
    }

    public void deleteRow(Row row){
        rows_live.postValue(repository.deleteRow(row));
    }

    public void loadMore(){
        page+=1;
        rows_live.postValue(repository.loadNextRow(page));
    }

    //onReturn 대체
    public interface OnReturnListener{
        void returnList(ArrayList<Row> list);
    }

    private OnReturnListener mListener = null;

    public void setOnReturnListener(OnReturnListener listener){
        this.mListener = listener;
    }
}
