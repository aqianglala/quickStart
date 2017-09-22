package com.example.mynewbaseapp.http;


import com.example.mynewbaseapp.bean.LaunchImageBean;

import retrofit2.http.GET;
import rx.Observable;

/**
 * Created by zy1584 on 2017-3-27.
 */

public interface HttpService {

    @GET("http://news-at.zhihu.com/api/7/prefetch-launch-images/1080*1920")
    Observable<LaunchImageBean> getLaunchImage();

}
