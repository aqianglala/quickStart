package com.example.mynewbaseapp.base;


import com.example.mynewbaseapp.http.Http;
import com.example.mynewbaseapp.http.HttpService;
import com.example.mynewbaseapp.mvp.IModel;

public class BaseModel implements IModel {
    protected static HttpService httpService;

    //初始化httpService
    static {
        httpService = Http.getHttpService();
    }

}
