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
        repository.setOnFailureListener(new RepositoryImpl2.OnFailureListener() {
            @Override
            public void onFailure() {
                mFailure.onFailure();
            }
        });
        repository.setOnReturnListener(new RepositoryImpl2.OnReturnListener() {
            @Override
            public void onReturn(ArrayList<Row> rows) {
                rows_live.postValue(rows);
            }
        });
        repository.getHospitalItem(Constants2.START_PAGE);
        //미리 수신 받을 대기를 해놓고 요청해야
        //옵저버 및 리스너 모두 동일
        //응답이 빠른 경우는 누락이 생길 수 있음. 이미 응답이 온 상태에서 리스너 등록이 안되어 있으면 받을 수 없다.
        //받을 준비를 하고 던지도록.
    }

    public void deleteRow(Row row){
        rows_live.postValue(repository.deleteRow(row));
    }

    public void loadMore(){
        page+=1;
        rows_live.postValue(repository.loadNextRow(page));
        //
    }

    public interface OnFailure{
        void onFailure();
    }
    private OnFailure mFailure = null;
    public void setOnFailure(OnFailure onFailure){
        this.mFailure = onFailure;
    }
}
