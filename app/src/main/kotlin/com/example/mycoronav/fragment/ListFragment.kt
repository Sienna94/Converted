package com.example.mycoronav.fragment

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.mycoronav.adapter.ListViewAdapter
import com.example.mycoronav.databinding.FragmentListBinding
import com.example.mycoronav.viewmodel.SharedViewModel
import com.example.mycoronav.vo.Row
import java.util.*
import kotlin.collections.ArrayList

class ListFragment : Fragment(), SwipeRefreshLayout.OnRefreshListener {
    private lateinit var binding: FragmentListBinding

    //adapter
    private lateinit var listViewAdapter: ListViewAdapter
    private lateinit var linearLayoutManager: LinearLayoutManager

    // Use the 'by activityViewModels()' Kotlin property delegate
    // from the fragment-ktx artifact
    private val model: SharedViewModel by activityViewModels()

    //interface
    private lateinit var listRequestListener: ListRequestListener


    override fun onAttach(context: Context) {
        super.onAttach(context)
        listRequestListener = context as ListRequestListener
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentListBinding.inflate(inflater, container, false)
        //set Swipe Refresh
        binding.swipeRefresh.setOnRefreshListener { onRefresh() }
        return binding.root
    }

    //onActivityCreated is after 'onCreatedView'
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        //live data
        val dataObserver: androidx.lifecycle.Observer<ArrayList<Row>> =
            androidx.lifecycle.Observer {
                setList(it)
            }
        model.rows_live.observe(viewLifecycleOwner, dataObserver)
    }

    private fun setList(rowItem: ArrayList<Row>) {
        //set RecyclerView
        context?.run {
            listViewAdapter = ListViewAdapter(this)
        }
        listViewAdapter.rowItem = rowItem
        linearLayoutManager = LinearLayoutManager(requireContext())
        binding.rvListView.apply {
            this.adapter = listViewAdapter
            this.layoutManager = linearLayoutManager
        }
        listViewAdapter.onClickDel = {
            model.deleteRow(it)
            listViewAdapter.notifyDataSetChanged()
        }
        listViewAdapter.notifyDataSetChanged()

        //for infinite scroll, add scroll Listener
        binding.rvListView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                //solving 2 (by position)
                //last visible item's position
//                val lastVisibleItemPosition = (binding.rvListView.layoutManager as LinearLayoutManager).findLastVisibleItemPosition()
//                val itemTotalCount = binding.rvListView.adapter?.itemCount?.minus(1)
//                if(lastVisibleItemPosition == itemTotalCount){
//                    Log.d("ddd", "onScrolled: by position")
//
//                }

                //solving 1 (by scroll)
                //check if scroll touched the bottom
                //by whether it can scroll more vertically
                if(!binding.rvListView.canScrollVertically(1)){
                    Log.d("ddd", "onScrolled: by scroll")
                    model.loadMore()
                }
            }
        })
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