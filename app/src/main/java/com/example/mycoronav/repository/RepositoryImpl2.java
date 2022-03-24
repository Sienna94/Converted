package com.example.mycoronav.repository;

import android.util.Log;

import com.example.mycoronav.common.Constants;
import com.example.mycoronav.network.RetrofitClient2;
import com.example.mycoronav.network.RetrofitService2;
import com.example.mycoronav.vo.Row;
import com.example.mycoronav.vo2.Hospital;
import com.example.mycoronav.vo2.Item;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public final class RepositoryImpl2 implements Repository2 {
    //싱글턴 구현
    private static RepositoryImpl2 instance = new RepositoryImpl2();
    private RepositoryImpl2(){}
    public static RepositoryImpl2 getInstance() {
        return instance;
    }

    private Retrofit retrofit = RetrofitClient2.getInstance().get();
    ArrayList<Row> rows = new ArrayList<>();
    RetrofitService2 retrofitService = retrofit.create(RetrofitService2.class);
    //onReturn 구현 필요

    @Override
    public void getHospitalItem(int pageNum) {
        ArrayList<Row> currentRows = new ArrayList<>();
        Map<String, String> params = new HashMap<>();
        params.put("serviceKey", Constants.KEY);
        params.put("pageNo", Integer.toString(pageNum));

        retrofitService.getHospitalList(params)
                .enqueue(new Callback<Hospital>() {
                    @Override
                    public void onResponse(Call<Hospital> call, Response<Hospital> response) {
                        if (response.isSuccessful()) {
                            if (response.isSuccessful()) {
                                Log.d("ddd", "response.code =" + response.code());
                                Log.d("ddd", "response.message =" + response.message());

                                Hospital result = response.body();
//                                Iterator iterator = result.getBody().getItems().getItem().iterator();

                                for(int i =0; i < result.getBody().getItems().getItem().size(); i++){
                                    Item item = result.getBody().getItems().getItem().get(i);

                                    Row row = new Row();
                                    row.setCorona19Id(item.getSidoCdNm());
                                    row.setCorona19Date(item.getAddr());
                                    row.setCorona19ContactHistory(item.getYadmNm());

                                    currentRows.add(row);
                                }

                                if(pageNum == 1){
                                    rows = currentRows;
                                    mListener.onReturn(rows);
                                }else{
                                    rows.addAll(currentRows);
                                }
                            }else{
                                Log.d("ddd", "onResponse: not notSuccessful=" + response.code());
                            }
                        }else{
                            Log.d("ddd", "onResponse: not notSuccessful=" + response.message());
                        }
                    }

                    @Override
                    public void onFailure(Call<Hospital> call, Throwable t) {
                        Log.d("ddd", "onFailure: t = ${t.message}");
                    }
                });
    }

    @Override
    public ArrayList<Row> deleteRow(Row row) {
        if(rows != null){
            rows.remove(row);
        }
        return rows;
    }

    @Override
    public ArrayList<Row> loadNextRow(int pageNum) {
        //load next row
        getHospitalItem(pageNum);
        return rows;
    }

    //Interface
    public interface OnReturnListener{
        void onReturn (ArrayList<Row> rows);
    }
    private OnReturnListener mListener = null;
    public void setOnReturnListener(OnReturnListener listener){
        this.mListener = listener;
    }
}
