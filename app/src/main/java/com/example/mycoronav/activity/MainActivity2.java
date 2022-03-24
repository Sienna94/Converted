package com.example.mycoronav.activity;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import com.example.mycoronav.R;
import com.example.mycoronav.databinding.ActivityMainBinding;
import com.example.mycoronav.fragment.GridFragment;
import com.example.mycoronav.fragment.ListFragment;
import com.example.mycoronav.fragment.ScrollFragment;
import com.example.mycoronav.fragment.ViewPagerFragment2;
import com.example.mycoronav.viewmodel.SharedViewModel2;

public class MainActivity2 extends AppCompatActivity implements ListFragment.ListRequestListener, GridFragment.ListRequestListener, ScrollFragment.ListRequestListener {
    private ActivityMainBinding binding;
    private ViewPagerFragment2 viewPagerFragment;
    private SharedViewModel2 sharedViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        //binding.activity = this
        viewPagerFragment = new ViewPagerFragment2();
        sharedViewModel = new ViewModelProvider(this).get(SharedViewModel2.class);


        //옵저빙은 호출 메소드보다 먼저 시작되어야 한다.
//        setFragments();
        setFragments();
        sharedViewModel.rows_live.observe(this, rows ->{
            //send data to frags
            viewPagerFragment.listFragment.setList(rows);
        });
        getCoronaList();
        //프래그먼트를 세팅 후 통신값을 받아야한다.
        // UI 세팅 -> 통신 요청 -> progress bar ->
        //프래그먼트를 세팅후 값을 기다리는 동안 progress bar로 기다리지 않도록
        // ui에 데이터 바인딩 이후 progress 종료 (사용자가 잘못된 클릭을 하지 않도록)
        // 터치를 막는 view 를 progressbar 와 화면 사이에 끼워놓을 것. (내일 수정)
        //로딩 프로그레스, 투명 터치 제한 뷰, 아이템 삭제
    }

   private void setFragments(){
        //insert fragment to FrameLayout
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.my_layout, viewPagerFragment);
        transaction.commit();
        //set listener
    }

    private void getCoronaList(){
        binding.progressCircular.setVisibility(View.VISIBLE);
        sharedViewModel.getRows();
        binding.progressCircular.setVisibility(View.GONE);
    }

    @Override
    public void requestList() {
        getCoronaList();
    }
}