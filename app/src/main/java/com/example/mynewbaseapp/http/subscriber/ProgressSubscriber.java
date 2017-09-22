package com.example.mynewbaseapp.http.subscriber;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;

import com.example.mynewbaseapp.base.BaseSubscriber;
import com.example.mynewbaseapp.http.exception.ApiException;
import com.example.mynewbaseapp.utils.NetUtils;
import com.orhanobut.logger.Logger;


/**
 * Created by tzqiang on 2016/11/6.
 */

public abstract class ProgressSubscriber<T> extends BaseSubscriber<T> {

    private Context context;
    /*加载框可自己定义*/
    private ProgressDialog pd;

    public ProgressSubscriber(Context context, boolean isCancelable) {
        this.context = context;
        initProgressDialog(isCancelable);
    }

    private static final String TAG = "CommonSubscriber";

    @Override
    public void onStart() {
        showProgressDialog();
        if (!NetUtils.isConnected(context)) {
            Logger.t(TAG).e("网络不可用");
        } else {
            Logger.t(TAG).e("网络可用");
        }
    }


    @Override
    protected void onError(ApiException e) {
        Logger.t(TAG).e(e, "错误信息为 " + "code:" + e.getMessage());
        dismissProgressDialog();
    }

    @Override
    public void onCompleted() {
        Logger.t(TAG).e("成功了");
        dismissProgressDialog();
    }

    /**
     * 初始化加载框
     */
    private void initProgressDialog(boolean cancel) {
        if (pd == null && context != null) {
            pd = new ProgressDialog(context);
            pd.setCancelable(cancel);
            if (cancel) {
                pd.setOnCancelListener(new DialogInterface.OnCancelListener() {
                    @Override
                    public void onCancel(DialogInterface dialogInterface) {
                        onCancelProgress();
                    }
                });
            }
        }
    }

    /**
     * 取消ProgressDialog的时候，取消对observable的订阅，同时也取消了http请求
     */
    public void onCancelProgress() {
        if (!this.isUnsubscribed()) {
            this.unsubscribe();
        }
    }


    /**
     * 显示加载框
     */
    private void showProgressDialog() {
        if (pd == null || context == null) return;
        if (!pd.isShowing()) {
            pd.show();
        }
    }

    /**
     * 隐藏
     */
    private void dismissProgressDialog() {
        if (pd != null && pd.isShowing()) {
            pd.dismiss();
        }
    }

}
