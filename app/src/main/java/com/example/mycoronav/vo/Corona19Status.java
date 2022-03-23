package com.example.mycoronav.vo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Corona19Status {
    @SerializedName("list_total_count")
    @Expose
    private Integer listTotalCount;
    @SerializedName("RESULT")
    @Expose
    public Result result;
    @SerializedName("row")
    @Expose
    private List<Row> row = null;

    public Integer getListTotalCount() {
        return listTotalCount;
    }

    public void setListTotalCount(Integer listTotalCount) {
        this.listTotalCount = listTotalCount;
    }

    public Result getResult() {
        return result;
    }

    public void setResult(Result result) {
        this.result = result;
    }

    public List<Row> getRow() {
        return row;
    }

    public void setRow(List<Row> row) {
        this.row = row;
    }
}