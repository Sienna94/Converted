package com.example.mycoronav.fragment

import android.content.Context
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout

import com.example.mycoronav.databinding.FragmentScrollBinding
import com.example.mycoronav.view.ScrollViewItem
import com.example.mycoronav.viewmodel.SharedViewModel2
import com.example.mycoronav.vo.Row

import kotlinx.android.synthetic.main.fragment_scroll.*

class ScrollFragment : Fragment(), SwipeRefreshLayout.OnRefreshListener {
    private lateinit var binding: FragmentScrollBinding

    //viewModel
    private val model: SharedViewModel2 by activityViewModels()

    //interface
    private lateinit var listRequestListener: ListRequestListener

    override fun onAttach(context: Context) {
        super.onAttach(context)
        listRequestListener = context as ListRequestListener
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        //live data
        val dataObserver: Observer<ArrayList<Row>> =
            Observer {
                setList(it)
            }
        model.rows_live.observe(viewLifecycleOwner, dataObserver)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentScrollBinding.inflate(inflater, container, false)
        //set swipe
        binding.swipeRefresh.setOnRefreshListener { onRefresh() }

        return binding.root
    }

    private fun setList(rowItem: ArrayList<Row>) {
        //getData
        binding.scrollviewLayout.removeAllViews()
        for (row in rowItem) {
            val scrollviewItem = ScrollViewItem(requireContext())
            scrollviewItem.bind(row)
            scrollviewItem.onClickDel = {
                model.deleteRow(it)
                scrollview_layout.removeView(scrollviewItem)
            }
            binding.scrollviewLayout.addView(scrollviewItem)
        }
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