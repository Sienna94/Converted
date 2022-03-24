package com.example.mycoronav.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import java.util.ArrayList;

public class ViewPagerAdapter2 extends FragmentStateAdapter {
    public ArrayList<Fragment> fragments;

    public ViewPagerAdapter2(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
        this.fragments = new ArrayList<>();
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
       return fragments.get(position);
    }

    @Override
    public int getItemCount() {
        return fragments.size();
    }

    public void addFragment(Fragment fragment){
        fragments.add(fragment);
        notifyItemInserted(fragments.size() - 1);
    }

    public void removeFragments(){
        fragments.remove(fragments.size() -1);
        notifyItemRemoved(fragments.size());
    }
}
