package com.example.mynewbaseapp.modules.test;


import com.example.mynewbaseapp.base.BaseModel;
import com.example.mynewbaseapp.bean.LaunchImageBean;

import rx.Observable;

public class TestModel extends BaseModel {

    public Observable<LaunchImageBean> getLaunchImage(){
        return httpService.getLaunchImage();
    }
}
