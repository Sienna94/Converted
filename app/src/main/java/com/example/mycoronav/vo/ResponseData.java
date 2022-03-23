package com.example.mycoronav.vo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ResponseData {
    @SerializedName("Corona19Status")
    @Expose
    private Corona19Status corona19Status;

    public Corona19Status getCorona19Status() {
        return corona19Status;
    }

    public void setCorona19Status(Corona19Status corona19Status) {
        this.corona19Status = corona19Status;
    }

}
