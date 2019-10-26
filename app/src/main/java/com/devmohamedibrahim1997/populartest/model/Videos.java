package com.devmohamedibrahim1997.populartest.model;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Videos {


    @SerializedName("key")
    @Expose
    private String key;


    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

}
