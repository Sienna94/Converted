package com.example.mycoronav.repository

import com.example.mycoronav.vo.Row

interface Repository {
    fun getHospitalItem(pageNum: Int)
    fun deleteRow(row: Row): ArrayList<Row>
    fun loadNextRow(pageNum: Int): ArrayList<Row>
}