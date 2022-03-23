package com.example.mycoronav.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.example.mycoronav.R
import com.example.mycoronav.adapter.ViewPagerAdapter
import com.example.mycoronav.databinding.FragmentViewPagerBinding
import com.google.android.material.tabs.TabLayoutMediator

class ViewPagerFragment : Fragment() {
    private lateinit var binding: FragmentViewPagerBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentViewPagerBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val pagerAdapter = ViewPagerAdapter(requireActivity())
        //add fragments to adapter
        pagerAdapter.addFragment(ListFragment())
        pagerAdapter.addFragment(GridFragment())
        pagerAdapter.addFragment(ScrollFragment())
        //adapter
        binding.viewPager.adapter = pagerAdapter
        binding.viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                binding.indicator.selectDot(position)
            }
        })
        //attach TabLayout
        TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
            when (position) {
                0 -> {
                    tab.apply {
                        this.text = "ListView"
                        this.icon =
                            ContextCompat.getDrawable(requireContext(), R.drawable.icon_mask4)
                    }
                }
                1 -> {
                    tab.apply {
                        this.text = "GridView"
                        this.icon =
                            ContextCompat.getDrawable(requireContext(), R.drawable.icon_mask4)
                    }
                }
                2 -> {
                    tab.apply {
                        this.text = "ScrollView"
                        this.icon =
                            ContextCompat.getDrawable(requireContext(), R.drawable.icon_mask4)
                    }
                }
            }
        }.attach()
        //indicator
        binding.indicator.createDotPanel(
            pagerAdapter.itemCount,
            R.drawable.dot_not_selected,
            R.drawable.dot_selected,
            0
        )
    }
}