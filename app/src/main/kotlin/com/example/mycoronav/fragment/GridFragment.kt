package com.example.mycoronav.fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.mycoronav.adapter.GridViewAdapter
import com.example.mycoronav.databinding.FragmentGridBinding
import com.example.mycoronav.viewmodel.SharedViewModel
import com.example.mycoronav.vo.Row

class GridFragment : Fragment(), SwipeRefreshLayout.OnRefreshListener {
    private lateinit var binding: FragmentGridBinding
    //adapter
    private lateinit var gridViewAdapter: GridViewAdapter
    private lateinit var gridLayoutManager: GridLayoutManager
    //viewModel
    private val model: SharedViewModel by activityViewModels()

    private lateinit var listRequestListener: ListRequestListener

    override fun onAttach(context: Context) {
        super.onAttach(context)
        listRequestListener = context as ListRequestListener
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        //live data
        val dataObserver: Observer<ArrayList<Row>> =
            Observer{
                setList(it)
            }
        model.rows_live.observe(viewLifecycleOwner, dataObserver)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentGridBinding.inflate(inflater, container, false)
        //set swipe
        binding.swipeRefresh.setOnRefreshListener { onRefresh() }
        return binding.root
    }

    private fun setList(rowItem : ArrayList<Row>){
        //gridView Adapter
        gridViewAdapter = GridViewAdapter(requireContext())
        gridViewAdapter.rowItem = rowItem
        gridLayoutManager = GridLayoutManager(requireContext(), 2)
        binding.rvGridView.apply {
            this.adapter = gridViewAdapter
            this.layoutManager = gridLayoutManager
        }
        gridViewAdapter.onClickDel = {
            model.deleteRow(it)
            gridViewAdapter.notifyDataSetChanged()
        }
        gridViewAdapter.notifyDataSetChanged()
    }

    override fun onRefresh() {
        listRequestListener.requestList()
        binding.swipeRefresh.isRefreshing = false
    }
    //listener
    interface ListRequestListener {
        fun requestList()
    }
}