package com.example.mycoronav.repository;

import android.util.Log;

import com.example.mycoronav.common.Constants2;
import com.example.mycoronav.network.RetrofitClient2;
import com.example.mycoronav.network.RetrofitService2;
import com.example.mycoronav.vo.Row;
import com.example.mycoronav.vo2.Hospital;
import com.example.mycoronav.vo2.Item;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public final class RepositoryImpl2 implements Repository2 {
    //싱글턴 구현
    //final 은 클래스 상속을 막는다.
    //final 을 메소드 앞에 쓰면 오버라이딩이 안됨
    // 오버로딩 : 동일한 메소드를
    // 오버라이딩은 재정의. 상속관계가 전제
    //final
    // static final : 해당 클래스 내에서 유일한 static, final 은 변경이 안되므로 상수 기능을 하는 것.

    private static final RepositoryImpl2 instance = new RepositoryImpl2();
    private RepositoryImpl2(){}
    public static RepositoryImpl2 getInstance() {
        return instance;
    }
    //싱글턴과 멀티스레드 상황 숙지.

    private Retrofit retrofit = RetrofitClient2.INSTANCE.get();
    RetrofitService2 retrofitService = retrofit.create(RetrofitService2.class);
    ArrayList<Row> rows = new ArrayList<>();
    //들고 있을 필요가 없는 rows
    // currenRows 와의 용도 구분이 뚜렷하지 않음.

    @Override
    public void getHospitalItem(int pageNum) {
        ArrayList<Row> currentRows = new ArrayList<>();
        Map<String, String> params = new HashMap<>();
        params.put("serviceKey", Constants2.KEY);
        params.put("pageNo", Integer.toString(pageNum));

        retrofitService.getHospitalList(params)
                .enqueue(new Callback<Hospital>() {
                    @Override
                    public void onResponse(Call<Hospital> call, Response<Hospital> response) {
                        if (response.isSuccessful()) {
                            //successful 중
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
                            Log.d("ddd", "onResponse: not notSuccessful=" + response.message());
                        }
                    }

                    @Override
                    public void onFailure(Call<Hospital> call, Throwable t) {
                        Log.d("ddd", "onFailure: t = ${t.message}");
                        // error 시 문구 alert 팝업 노출
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
