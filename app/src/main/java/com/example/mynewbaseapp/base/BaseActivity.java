package com.example.mynewbaseapp.base;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.CallSuper;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;

import com.example.mynewbaseapp.mvp.IView;
import com.example.mynewbaseapp.utils.ToastUtils;
import com.orhanobut.logger.Logger;

import butterknife.ButterKnife;
import me.yokeyword.fragmentation.SupportActivity;

/**
 * Created by zy1584 on 2017-3-27.
 */

public abstract class BaseActivity<P extends BasePresenter> extends SupportActivity implements
        IView {
    protected View view;
    protected P mPresenter;

    protected String TAG;
    protected LayoutInflater mInflater;
    protected Activity mActivity;

    @Override
    @CallSuper
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (isFullScreen()) {// 隐藏android系统的状态栏
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                    WindowManager.LayoutParams.FLAG_FULLSCREEN);
        }
        mPresenter = loadPresenter();
        attachView();

        initMembers();
        setContentView(getView());

        ButterKnife.bind(this);
        initInjector();
        initView();
        setListener();
        if (null != getIntent()) {
            handleIntent(getIntent());
        }
        doBusiness();
    }

    private void attachView() {
        if (mPresenter != null)
            mPresenter.attachView(this);
    }

    private void initMembers() {
        TAG = this.getClass().getSimpleName();
        mInflater = getLayoutInflater();
        mActivity = this;
    }

    /**
     * @return 显示的内容
     */
    private View getView() {
        view = View.inflate(this, getLayoutId(), null);
        return view;
    }

    /******************************************* protected方法 ***********************************************/

    protected boolean isFullScreen() {
        return false;
    }

    protected void handleIntent(Intent intent) {
    }

    /******************************************* abstract方法 ***********************************************/

    /**
     * 绑定布局文件
     *
     * @return
     */
    protected abstract int getLayoutId();

    /**
     * 创建presenter
     *
     * @return
     */
    protected abstract P loadPresenter();

    /**
     * Dagger 注入
     */
    protected abstract void initInjector();

    /**
     * 初始化视图控件
     */
    protected abstract void initView();

    /**
     * 设置监听器
     */
    protected abstract void setListener();

    /**
     * 处理业务逻辑
     */
    protected abstract void doBusiness();

    /******************************************* activity生命周期封装及rxlife管理 ***********************************************/

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mPresenter != null) {
            mPresenter.unsubscribe();// rx生命周期管理
            mPresenter.detachView();
        }
    }

    /******************************************* 高频操作封装 ***********************************************/
    /**
     * 打开一个Activity 默认 不关闭当前activity
     */
    public void gotoActivity(Class<?> clz) {
        gotoActivity(clz, false, null);
    }

    public void gotoActivity(Class<?> clz, boolean isCloseCurrentActivity) {
        gotoActivity(clz, isCloseCurrentActivity, null);
    }

    public void gotoActivity(Class<?> clz, boolean isCloseCurrentActivity, Bundle ex) {
        Intent intent = new Intent(this, clz);
        if (ex != null) intent.putExtras(ex);
        startActivity(intent);
        if (isCloseCurrentActivity) {
            finish();
        }
    }

    /**
     * @param str 显示一个内容为str的toast
     */
    public void toast(String str) {
        ToastUtils.showShort(this, str);
    }

    /**
     * @param contentId 显示一个内容为contentId指定的toast
     */
    public void toast(int contentId) {
        ToastUtils.showShort(this, contentId);
    }

    /**
     * @param str 日志的处理
     */
    public void logI(String str) {
        Logger.t(TAG).i(str);
    }

}
