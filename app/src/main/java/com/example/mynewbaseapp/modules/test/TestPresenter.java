package com.example.mynewbaseapp.modules.test;


import com.example.mynewbaseapp.base.BasePresenter;
import com.example.mynewbaseapp.bean.LaunchImageBean;
import com.example.mynewbaseapp.http.transformer.ScheduleTransformer;

import rx.Subscriber;
import rx.Subscription;

public class TestPresenter extends BasePresenter<TestActivity> implements TestContract.Presenter {

    private TestModel mTestModel;

    public TestPresenter() {
        mTestModel = new TestModel();
    }


    @Override
    public void getLaunchImage() {
        Subscription subscribe = mTestModel.getLaunchImage().compose(new ScheduleTransformer<LaunchImageBean>())
                .subscribe(new Subscriber<LaunchImageBean>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(LaunchImageBean launchImageBean) {
                        if (launchImageBean != null){
                            getIView().toast("加载成功了");
                        }
                    }
                });
        addSubscription(subscribe);
    }
}
