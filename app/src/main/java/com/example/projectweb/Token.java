package com.example.projectweb;

import com.google.gson.annotations.SerializedName;

public class Token {
    @SerializedName("refresh")
    String refresh;
    @SerializedName("access")
    String access;

    public Token(String access,String refresh){
        this.access = access;
        this.refresh = refresh;
    }


    String getRefresh(){
        return this.refresh;
    }
    void setRefresh(String refresh){
        this.access = refresh;
    }

    String getAccess(){
        return this.access;
    }
    void setAccess(String access){
        this.access=access;
    }

}
