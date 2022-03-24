package com.example.mycoronav.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

import com.example.mycoronav.common.Constants
import com.example.mycoronav.repository.RepositoryImpl
import com.example.mycoronav.repository.RepositoryImpl2
import com.example.mycoronav.vo.Row

class SharedViewModel : ViewModel() {
    //live data
    var rows_live: MutableLiveData<ArrayList<Row>> = MutableLiveData<ArrayList<Row>>()
    var repository = RepositoryImpl2.getInstance();
//    var repository = RepositoryImpl
    //infinite scroll paging
    private var page = 1

    // 페이지당 item 10개
    fun getRows() {
        repository.getHospitalItem(Constants.START_PAGE)
//        repository.onReturn = {
//            rows_live.postValue(it)
//        }
        repository.setOnReturnListener(object: RepositoryImpl2.OnReturnListener{
            override fun onReturn(rows: java.util.ArrayList<Row>?) {
                rows_live.postValue(rows);
            }
        })
    }

    fun deleteRow(row: Row) {
        val rows_deleted = repository.deleteRow(row)
        rows_live.postValue(rows_deleted)
    }

    fun loadMore() {
        page+=1
        val rows_moreLoaded = repository.loadNextRow(page)
        rows_live.postValue(rows_moreLoaded)
    }
}