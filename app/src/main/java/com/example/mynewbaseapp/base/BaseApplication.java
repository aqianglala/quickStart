package com.example.mynewbaseapp.base;

import android.app.Application;
import android.content.Context;
import android.os.Handler;
import android.os.Looper;

import com.example.mynewbaseapp.injector.components.ApplicationComponent;
import com.example.mynewbaseapp.injector.components.DaggerApplicationComponent;
import com.example.mynewbaseapp.injector.modules.ApplicationModule;
import com.example.mynewbaseapp.utils.CrashHandler;
import com.example.mynewbaseapp.utils.ForegroundCallbacks;
import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.BuildConfig;
import com.orhanobut.logger.Logger;
import com.tencent.bugly.crashreport.CrashReport;

public class BaseApplication extends Application {

    private static Context context;
    private static Thread mainThread;
    private static long mainThreadId;
    private static Handler mainHandler;
    private static Looper mainlooper;
    private ApplicationComponent mApplicationComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        context = this;

        getMainThreadData();
        ForegroundCallbacks.init(this);
        CrashHandler.getInstance().init(getApplicationContext());

//        LeakCanary.install(this);
        // 腾讯bugly
        CrashReport.initCrashReport(getApplicationContext(), "693d83a8db", true);

        Logger.addLogAdapter(new AndroidLogAdapter() {
            @Override
            public boolean isLoggable(int priority, String tag) {
                return BuildConfig.DEBUG;
            }
        });
        mApplicationComponent = DaggerApplicationComponent
                .builder()
                .applicationModule(new ApplicationModule(this))
                .build();
    }

    public ApplicationComponent getApplicationComponent() {
        return mApplicationComponent;
    }

    private void getMainThreadData() {
        mainThread = Thread.currentThread();//获取主线程

        mainThreadId = android.os.Process.myTid();//获取当前的线程id

        mainHandler = new Handler(); //在主线程初始化一个全局的handler。

        mainlooper = getMainLooper();// 获取主线程的looper
    }

    public static Context getContext() {
        return context;
    }

    public static Thread getMainThread() {
        return mainThread;
    }

    public static long getMainThreadId() {
        return mainThreadId;
    }

    public static Handler getMainHandler() {
        return mainHandler;
    }

    public static Looper getMainlooper() {
        return mainlooper;
    }

}
