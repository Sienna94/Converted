package com.example.mycoronav.repository

import android.util.Log
import com.example.mycoronav.common.Constants
import com.example.mycoronav.network.RetrofitClient
import com.example.mycoronav.network.RetrofitService
import com.example.mycoronav.vo.ResponseData
import com.example.mycoronav.vo.Row
import com.example.mycoronav.vo2.Hospital
import com.example.mycoronav.vo2.Item
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

object RepositoryImpl : Repository {
    private val retrofit = RetrofitClient.getInstance()
    var rows: ArrayList<Row> = ArrayList()
    val retrofitService = retrofit.create(RetrofitService::class.java)
    var onReturn: ((ArrayList<Row>) -> Unit)? = null

    override fun getHospitalItem(pageNum: Int) {
        val currentRows = ArrayList<Row>()

        val params = mapOf(
            "serviceKey" to Constants.KEY,
            "pageNo" to pageNum.toString()
        )
        retrofitService.getHospitalList(
            params
        ).enqueue(object : Callback<Hospital> {
            override fun onResponse(call: Call<Hospital>, response: Response<Hospital>) {
                if (response.isSuccessful) {
                    if (response.isSuccessful) {
                        Log.d("ddd", "response.code = ${response.code()}")
                        Log.d("ddd", "response.message = ${response.message()}")
                        response.body()?.let {
                            for(item in it.body.items.item){
                                val row = Row().apply {
                                    this.corona19Id = item.sidoCdNm
                                    this.corona19Date = item.addr
                                    this.corona19ContactHistory = item.yadmNm
                                }
                                currentRows.add(row)
                            }
                            if(pageNum == 1){
                                onReturn?.invoke(currentRows)
                                rows = currentRows
                            }else{
                                rows.addAll(currentRows)
                            }
                        }
                    } else {
                        Log.d("ddd", "onResponse: not notSuccessful/ ${response.code()}")
                    }
                }else{
                    Log.d("ddd", "onResponse: not notSuccessful/ ${response.message()}")
                }
            }

            override fun onFailure(call: Call<Hospital>, t: Throwable) {
                Log.d("ddd", "onFailure: t = ${t.message}")
            }

        })
    }

    override fun deleteRow(row: Row): ArrayList<Row> {
        val rowsDeletedData: ArrayList<Row>
        rows.run {
            this.remove(row)
            rowsDeletedData = this
            rows = rowsDeletedData
        }
        return rows
    }

    override fun loadNextRow(pageNum: Int): ArrayList<Row> {
        //load Next Row
        getHospitalItem(pageNum)
        return rows
    }
}