package com.example.mycoronav.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.mycoronav.common.Constants2;
import com.example.mycoronav.repository.RepositoryImpl2;
import com.example.mycoronav.vo.Row;

import java.util.ArrayList;

public class SharedViewModel2 extends ViewModel {
    //livedata
    public MutableLiveData<ArrayList<Row>> rows_live = new MutableLiveData<>();
    private RepositoryImpl2 repository = RepositoryImpl2.getInstance();
    private int page = 1;

    public void getRows(){
        repository.getHospitalItem(Constants2.START_PAGE);
        repository.setOnReturnListener(new RepositoryImpl2.OnReturnListener() {
            @Override
            public void onReturn(ArrayList<Row> rows) {
                rows_live.postValue(rows);
            }
        });
    }

    public void deleteRow(Row row){
        rows_live.postValue(repository.deleteRow(row));
    }

    public void loadMore(){
        page+=1;
        rows_live.postValue(repository.loadNextRow(page));
    }
}
