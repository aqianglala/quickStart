package com.example.mynewbaseapp.utils;

import android.content.Context;

/**
 * Created by zy1584 on 2017-6-15.
 */

public class CrashHandler implements Thread.UncaughtExceptionHandler {
    private static CrashHandler INSTANCE ;
    private Context context;

    private CrashHandler(){
    }

    public static synchronized CrashHandler getInstance(){
        if (INSTANCE == null)
            INSTANCE = new CrashHandler();
        return INSTANCE;
    }

    public void init(Context context){
        this.context = context;
        Thread.setDefaultUncaughtExceptionHandler(this);
    }


    public void uncaughtException(Thread arg0, Throwable arg1) {
        System.out.println("程序挂掉了 ");
        // 在此可以把用户手机的一些信息以及异常信息捕获并上传,由于UMeng在这方面有很程序的api接口来调用，故没有考虑

        //干掉当前的程序
        android.os.Process.killProcess(android.os.Process.myPid());
    }

}