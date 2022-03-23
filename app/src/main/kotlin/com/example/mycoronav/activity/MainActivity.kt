package com.example.mycoronav.activity

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.mycoronav.R
import com.example.mycoronav.databinding.ActivityMainBinding
import com.example.mycoronav.fragment.GridFragment
import com.example.mycoronav.fragment.ListFragment
import com.example.mycoronav.fragment.ScrollFragment
import com.example.mycoronav.fragment.ViewPagerFragment
import com.example.mycoronav.viewmodel.SharedViewModel

class MainActivity : AppCompatActivity(), ListFragment.ListRequestListener,
    GridFragment.ListRequestListener, ScrollFragment.ListRequestListener {
    lateinit var binding: ActivityMainBinding
    private var viewPagerFragment = ViewPagerFragment()

    //viewModel
    val viewModel: SharedViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        //after splash showed, set Theme default again
        setTheme(R.style.AppTheme)
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.activity = this
        getCoronaList()
        viewModel.rows_live.run {
            setFragments()
        }
    }

    private fun setFragments() {
        //insert fragment to FrameLayout
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.my_layout, viewPagerFragment)
        transaction.commit()
    }

    private fun getCoronaList() {
        //show progressbar
        binding.progressCircular.visibility = View.VISIBLE
        viewModel.getRows()
        binding.progressCircular.visibility = View.GONE
    }

    override fun requestList() {
        getCoronaList()
    }
}