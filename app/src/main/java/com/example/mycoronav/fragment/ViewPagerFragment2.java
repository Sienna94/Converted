package com.example.mycoronav.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import com.example.mycoronav.R;
import com.example.mycoronav.adapter.ViewPagerAdapter2;
import com.example.mycoronav.databinding.FragmentViewPagerBinding;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class ViewPagerFragment2 extends Fragment {
    private FragmentViewPagerBinding binding;
    private ViewPagerAdapter2 viewPagerAdapter;
    public ListFragment2 listFragment;
    public GridFragment gridFragment;
    public ScrollFragment scrollFragment;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentViewPagerBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }
    //onResume 호출 후 빈번하게 호출될 가능성,
    //backstack entry 의 경우 굉장히 자주 호출될 가능성, view가 생
    //inflate는 무겁다. 이를 최소화하기 위해 조절해야함.
    //binding이 null체크 이후 return하도록
    //recyclerview 의 holder 패턴이 강제되는 이유와도 연관.

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        listFragment = new ListFragment2();
        gridFragment = new GridFragment();
        scrollFragment = new ScrollFragment();
        //add fragment to adapter
        viewPagerAdapter = new ViewPagerAdapter2(requireActivity());
        viewPagerAdapter.addFragment(listFragment);
        viewPagerAdapter.addFragment(gridFragment);
        viewPagerAdapter.addFragment(scrollFragment);
        //adpter
        binding.viewPager.setAdapter(viewPagerAdapter);
        binding.viewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
//                binding.indicator.selectDot(position);

            }
        });
        //attach TabLayout
        new TabLayoutMediator(binding.tabLayout, binding.viewPager, new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                switch (position) {
                    case 0:
                        binding.viewPager.setCurrentItem(0);
                        tab.setText("ListView");
                        tab.setIcon(ContextCompat.getDrawable(requireContext(), R.drawable.icon_mask4));
                        break;

                    case 1:
                        binding.viewPager.setCurrentItem(1);
                        tab.setText("GirdView");
                        tab.setIcon(ContextCompat.getDrawable(requireContext(), R.drawable.icon_mask4));
                        break;

                    case 2:
                        binding.viewPager.setCurrentItem(2);
                        tab.setText("ScrollView");
                        tab.setIcon(ContextCompat.getDrawable(requireContext(), R.drawable.icon_mask4));
                        break;

                }
            }
        }).attach();
        binding.viewPager.setCurrentItem(0);
    //indicator
//        binding.indicator.createDotPanel(
//                viewPagerAdapter.getItemCount(),
//                R.drawable.dot_not_selected,
//                R.drawable.dot_selected,
//                0
//        );

    }

}