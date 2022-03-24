package com.example.mycoronav.repository;

import com.example.mycoronav.vo.Row;

import java.util.ArrayList;

public interface Repository2 {
    void getHospitalItem(int pageNum);
    ArrayList<Row> deleteRow(Row row);
    ArrayList<Row> loadNextRow(int pageNum);
}
